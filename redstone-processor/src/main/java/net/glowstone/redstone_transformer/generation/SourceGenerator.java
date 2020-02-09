package net.glowstone.redstone_transformer.generation;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import net.glowstone.block.data.AbstractBlockDataManager;
import net.glowstone.block.data.BlockDataConstructor;
import net.glowstone.block.data.states.AbstractStatefulBlockData;
import net.glowstone.block.data.states.values.StateValue;
import net.glowstone.redstone_transformer.ingestion.IngestionResult;
import net.glowstone.redstone_transformer.ingestion.PropInterfaceData;
import net.glowstone.redstone_transformer.report.BlockReportManager;
import org.bukkit.Material;

public class SourceGenerator {
    private final Filer filer;

    public SourceGenerator(ProcessingEnvironment processingEnv) {
        this.filer = processingEnv.getFiler();
    }

    public void generateSources(IngestionResult ingestionResult, BlockReportManager blockReportManager) {
        Set<ManagerBlockDataDetails> managerBlockDataDetails = implementInterfaces(ingestionResult, blockReportManager);
        implementManager(ingestionResult, managerBlockDataDetails);
    }

    private Set<ManagerBlockDataDetails> implementInterfaces(IngestionResult ingestionResult, BlockReportManager blockReportManager) {
        Set<String> createdBlockDataClasses = new HashSet<>();
        Set<ManagerBlockDataDetails> managerBlockDataDetails = new HashSet<>();
        Map<String, PropInterfaceData> propInterfacesByPropName = ingestionResult.getPropInterfaces();
        String blockDataImplPackage = ingestionResult.getBlockDataImplPackage();

        blockReportManager.getBlockNameToProps().forEach((blockName, blockProps) -> {

            Set<String> propNames = blockProps.getValidProps().keySet();

            List<PropInterfaceData> propInterfaces = propNames.stream()
                .map(propInterfacesByPropName::get)
                .collect(Collectors.toList());

            List<String> interfaceNames = propInterfaces.stream()
                .map(PropInterfaceData::getInterfaceName)
                .sorted()
                .collect(Collectors.toList());

            if (interfaceNames.isEmpty()) {
                interfaceNames.add("Stateless");
            }

            String blockDataClassName = interfaceNames.stream()
                .collect(Collectors.joining("", "Glow", "BlockData"));

            if (!createdBlockDataClasses.contains(blockDataClassName)) {
                List<TypeName> extendedInterfaces = propInterfaces.stream()
                    .map((propInterfaceData) -> TypeName.get(propInterfaceData.getPropInterface()))
                    .sorted(Comparator.comparing(TypeName::toString))
                    .collect(Collectors.toList());

                ClassName generatedClassName = ClassName.get(blockDataImplPackage, blockDataClassName);
                ParameterizedTypeName mapStringStateValueTypeName = ParameterizedTypeName.get(
                    ClassName.get(Map.class),
                    ClassName.get(String.class),
                    ParameterizedTypeName.get(
                        ClassName.get(StateValue.class),
                        WildcardTypeName.subtypeOf(Object.class)
                    )
                );

                MethodSpec cloneConstructorSpec = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameters(Arrays.asList(
                        ParameterSpec.builder(ClassName.get(Material.class), "material", Modifier.FINAL).build(),
                        ParameterSpec.builder(mapStringStateValueTypeName, "stateValues", Modifier.FINAL).build()
                    ))
                    .addStatement("super(material, stateValues)")
                    .build();

                MethodSpec cloneMethodSpec = MethodSpec.methodBuilder("clone")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(generatedClassName)
                    .addStatement(
                        "final $T newStateValues = stateValues.entrySet().stream().collect($T.toMap(Map.Entry::getKey, (value) -> value.getValue().clone()))",
                        mapStringStateValueTypeName,
                        ClassName.get(Collectors.class)
                    )
                    .addStatement("return new $T(material, stateValues)", generatedClassName)
                    .build();

                TypeSpec blockDataTypeSpec = TypeSpec.classBuilder(blockDataClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addSuperinterfaces(extendedInterfaces)
                    .superclass(AbstractStatefulBlockData.class)
                    .addMethod(cloneConstructorSpec)
                    .addMethod(cloneMethodSpec)
                    .build();

                JavaFile blockDataJavaFile = JavaFile.builder(blockDataImplPackage, blockDataTypeSpec)
                    .indent("    ")
                    .build();

                try {
                    blockDataJavaFile.writeTo(filer);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }

                createdBlockDataClasses.add(blockDataClassName);
            }

            String materialName = blockName.replace("minecraft:", "").toUpperCase();
            int defaultStateId = blockProps.getDefaultState();
            BlockReportManager.InternalBlockState defaultState = blockReportManager.getBlockIdToBlockState().get(defaultStateId);
            Map<Integer, Map<String, String>> blockIds = blockReportManager.getBlockIdToBlockState().entrySet().stream()
                .filter((e) -> e.getValue().getBlockName().equals(blockName))
                .collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue().getProperties()));
            managerBlockDataDetails.add(new ManagerBlockDataDetails(
                materialName,
                blockDataClassName,
                blockIds,
                propInterfaces.stream()
                    .map((e) -> new ManagerStateReportDetails(e.getPropName(), defaultState.getProperties().get(e.getPropName()), blockProps.getValidProps().get(e.getPropName()), e.getReportType()))
                    .collect(Collectors.toSet())
            ));
        });

        return managerBlockDataDetails;
    }

    private void implementManager(IngestionResult ingestionResult, Set<ManagerBlockDataDetails> managerBlockDataDetails) {
        String blockDataImplPackage = ingestionResult.getBlockDataImplPackage();
        String blockDataManagerPackage = ingestionResult.getBlockDataManagerPackage();

        List<MethodSpec> individualBlockConstructors = new ArrayList<>();

        managerBlockDataDetails.forEach((detail) -> {
            List<CodeBlock> individualBlockDataConstructorBlocks = new ArrayList<>();
            individualBlockDataConstructorBlocks.add(
                CodeBlock.of(
                    "return BlockDataConstructor.builder($T.$L, $T::new)",
                    Material.class,
                    detail.getMaterialName(),
                    ClassName.get(blockDataImplPackage, detail.getBlockDataSimpleName())
                )
            );

            detail.getStateReportDetails().forEach((stateReportDetail) -> {
                individualBlockDataConstructorBlocks.add(
                    CodeBlock.of(
                        ".associatePropWithReport($S, new $T($S, $L))",
                        stateReportDetail.getPropName(),
                        stateReportDetail.getStateReportType(),
                        stateReportDetail.getDefaultValue(),
                        stateReportDetail.getValidValues().stream()
                            .map((v) -> CodeBlock.of("$S", v))
                            .collect(CodeBlock.joining(", "))
                    )
                );
            });

            detail.getBlockIds().forEach((id, props) -> {
                individualBlockDataConstructorBlocks.add(
                    Stream.concat(
                        Stream.of(CodeBlock.of(".associateId($L)", Integer.toString(id))),
                        props.entrySet().stream()
                            .map((e) -> CodeBlock.of(".withProp($S, $S)", e.getKey(), e.getValue()))
                    ).collect(CodeBlock.joining(""))
                );
            });

            individualBlockDataConstructorBlocks.add(
                CodeBlock.of(".build()")
            );

            individualBlockConstructors.add(
                MethodSpec.methodBuilder(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, detail.getMaterialName()))
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .returns(BlockDataConstructor.class)
                    .addStatement(CodeBlock.join(individualBlockDataConstructorBlocks, "\n"))
                    .build()
            );
        });

        ParameterizedTypeName blockDataConstructorsTypeName = ParameterizedTypeName.get(
            ClassName.get(Set.class),
            ClassName.get(BlockDataConstructor.class)
        );

        CodeBlock.Builder individualBlockConstructorCalls = CodeBlock.builder();
        individualBlockConstructors.forEach((method) -> individualBlockConstructorCalls.addStatement("blockDataConstructors.add($N())", method));

        MethodSpec createBlockDataConstructors = MethodSpec.methodBuilder("createBlockDataConstructors")
            .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
            .returns(blockDataConstructorsTypeName)
            .addStatement("final $T blockDataConstructors = new $T<>()", blockDataConstructorsTypeName, HashSet.class)
            .addCode(
                individualBlockConstructors.stream()
                    .map((method) -> CodeBlock.of("blockDataConstructors.add($N())", method))
                    .collect(CodeBlockStatementCollector.collect())
            )
            .addStatement("return $T.unmodifiableSet(blockDataConstructors)", Collections.class)
            .build();

        MethodSpec constructor = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addStatement("super($N())", createBlockDataConstructors)
            .build();

        TypeSpec blockDataManagerTypeSpec =  TypeSpec.classBuilder("BlockDataManager")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(AbstractBlockDataManager.class)
            .addMethods(individualBlockConstructors)
            .addMethod(createBlockDataConstructors)
            .addMethod(constructor)
            .build();

        JavaFile blockDataManagerJavaFile = JavaFile.builder(blockDataManagerPackage, blockDataManagerTypeSpec)
            .indent("    ")
            .build();

        try {
            blockDataManagerJavaFile.writeTo(filer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static class CodeBlockStatementCollector {
        public static Collector<CodeBlock, CodeBlockStatementCollector, CodeBlock> collect() {
            return Collector.of(
                CodeBlockStatementCollector::new,
                CodeBlockStatementCollector::addStatement,
                CodeBlockStatementCollector::merge,
                CodeBlockStatementCollector::combine
            );
        }

        private final CodeBlock.Builder builder;

        private CodeBlockStatementCollector() {
            this.builder = CodeBlock.builder();
        }

        public CodeBlockStatementCollector addStatement(CodeBlock codeBlock) {
            this.builder.addStatement(codeBlock);
            return this;
        }

        public CodeBlockStatementCollector merge(CodeBlockStatementCollector other) {
            CodeBlock otherBlock = other.builder.build();
            if (!otherBlock.isEmpty()) {
                this.builder.add(otherBlock);
            }
            return this;
        }

        public CodeBlock combine() {
            return builder.build();
        }
    }
}
