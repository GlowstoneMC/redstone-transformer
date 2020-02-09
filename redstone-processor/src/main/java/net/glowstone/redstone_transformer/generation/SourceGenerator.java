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
import net.glowstone.redstone_transformer.ingestion.PropReportMapping;
import net.glowstone.redstone_transformer.report.BlockReportManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;

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
        List<PropInterfaceData> allPropInterfaces = ingestionResult.getPropInterfaces();
        String blockDataImplPackage = ingestionResult.getBlockDataImplPackage();

        blockReportManager.getBlockNameToProps().forEach((blockName, blockProps) -> {

            Set<String> propNames = new HashSet<>(blockProps.getValidProps().keySet());
            List<PropInterfaceData> propInterfaces = new ArrayList<>();

            allPropInterfaces.forEach((propInterface) -> {
                Map<Boolean, Set<String>> propNamesByRequired = propInterface.getPropReportMappings().stream()
                    .collect(Collectors.groupingBy(PropReportMapping::isRequired, Collectors.mapping(PropReportMapping::getPropName, Collectors.toSet())));
                if (propNames.containsAll(propNamesByRequired.get(true))) {
                    propNames.removeAll(propNamesByRequired.values().stream().flatMap(Set::stream).collect(Collectors.toSet()));
                    propInterfaces.add(propInterface);
                }
            });

            if (!propNames.isEmpty()) {
                throw new IllegalStateException("Unmapped properties found for " + blockName + ": " + String.join(", ", propNames));
            }

            String[] splitBlockName = blockName.split(":");
            Material material = Material.getMaterial(new NamespacedKey(splitBlockName[0], splitBlockName[1]));

            String blockDataClassName;
            if (BlockData.class.isAssignableFrom(material.data)) {
                blockDataClassName = "Glow" + material.data.getSimpleName() + "BlockData";
            } else {
                List<String> interfaceNames = propInterfaces.stream()
                    .map(PropInterfaceData::getInterfaceName)
                    .sorted()
                    .collect(Collectors.toList());

                if (interfaceNames.isEmpty()) {
                    interfaceNames.add("Stateless");
                }

                blockDataClassName = interfaceNames.stream()
                    .collect(Collectors.joining("", "Glow", "BlockData"));
            }

            if (!createdBlockDataClasses.contains(blockDataClassName)) {
                List<TypeName> extendedInterfaces = propInterfaces.stream()
                    .map((propInterfaceData) -> TypeName.get(propInterfaceData.getAssociatedInterface()))
                    .sorted(Comparator.comparing(TypeName::toString))
                    .collect(Collectors.toCollection(ArrayList::new));

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

            int defaultStateId = blockProps.getDefaultState();
            BlockReportManager.InternalBlockState defaultState = blockReportManager.getBlockIdToBlockState().get(defaultStateId);
            Map<Integer, Map<String, String>> blockIds = blockReportManager.getBlockIdToBlockState().entrySet().stream()
                .filter((e) -> e.getValue().getBlockName().equals(blockName))
                .collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue().getProperties()));
            managerBlockDataDetails.add(new ManagerBlockDataDetails(
                material,
                blockDataClassName,
                blockIds,
                propInterfaces.stream()
                    .flatMap((propInterface) -> propInterface.getPropReportMappings().stream()
                        .filter((mapping) -> blockProps.getValidProps().containsKey(mapping.getPropName()))
                        .map((mapping) ->
                            new ManagerStateReportDetails(
                                mapping.getPropName(),
                                defaultState.getProperties().get(mapping.getPropName()),
                                blockProps.getValidProps().get(mapping.getPropName()),
                                mapping.getReportType()
                            )
                        )
                    )
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
                    "BlockDataConstructor.Builder builder= BlockDataConstructor.builder($T.$L, $T::new)",
                    Material.class,
                    detail.getMaterial(),
                    ClassName.get(blockDataImplPackage, detail.getBlockDataSimpleName())
                )
            );

            detail.getStateReportDetails().forEach((stateReportDetail) -> {
                individualBlockDataConstructorBlocks.add(
                    CodeBlock.of(
                        "builder.associatePropWithReport($S, new $T($S, $L))",
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
                        Stream.of(CodeBlock.of("builder.associateId($L)", Integer.toString(id))),
                        props.entrySet().stream()
                            .map((e) -> CodeBlock.of(".withProp($S, $S)", e.getKey(), e.getValue()))
                    ).collect(CodeBlock.joining(""))
                );
            });

            individualBlockDataConstructorBlocks.add(
                CodeBlock.of("return builder.build()")
            );

            individualBlockConstructors.add(
                MethodSpec.methodBuilder(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, detail.getMaterial().toString()))
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .returns(BlockDataConstructor.class)
                    .addCode(individualBlockDataConstructorBlocks.stream().collect(CodeBlockStatementCollector.collect()))
                    .build()
            );
        });

        ParameterizedTypeName blockDataConstructorsTypeName = ParameterizedTypeName.get(
            ClassName.get(Set.class),
            ClassName.get(BlockDataConstructor.class)
        );

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
