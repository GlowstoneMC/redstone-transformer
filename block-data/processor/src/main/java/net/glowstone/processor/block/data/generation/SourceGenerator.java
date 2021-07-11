package net.glowstone.processor.block.data.generation;

import com.google.common.base.CaseFormat;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import net.glowstone.block.data.states.reports.StateReport;
import net.glowstone.block.data.states.values.StateValue;
import net.glowstone.processor.block.data.ingestion.PropInterfaceData;
import net.glowstone.processor.block.data.ingestion.PropPolyfillData;
import net.glowstone.processor.block.data.ingestion.PropReportMapping;
import net.glowstone.processor.block.data.ingestion.IngestionResult;
import net.glowstone.processor.block.data.report.BlockReportManager;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class SourceGenerator {
    private final Filer filer;

    public SourceGenerator(ProcessingEnvironment processingEnv) {
        this.filer = processingEnv.getFiler();
    }

    public void generateSources(IngestionResult ingestionResult, BlockReportManager blockReportManager) {
        Set<ManagerBlockDataDetails> managerBlockDataDetails = implementInterfaces(ingestionResult, blockReportManager);
        Set<String> blockManagerClassNames = implementBlockManagers(ingestionResult, managerBlockDataDetails);
        implementManager(ingestionResult, blockManagerClassNames);
    }

    private Set<ManagerBlockDataDetails> implementInterfaces(IngestionResult ingestionResult, BlockReportManager blockReportManager) {
        Set<String> createdBlockDataClasses = new HashSet<>();
        Set<ManagerBlockDataDetails> managerBlockDataDetails = new HashSet<>();
        List<PropInterfaceData> allPropInterfaces = ingestionResult.getPropInterfaces();
        String blockDataImplPackage = ingestionResult.getBlockDataImplPackage();

        Multimap<Class<? extends BlockData>, PropPolyfillData> polyfillData = HashMultimap.create();
        ingestionResult.getPropPolyfills().forEach((propPolyfillData) -> {
            propPolyfillData.getReplacesInterfaces().forEach((replacesInterface) -> {
                polyfillData.put(replacesInterface, propPolyfillData);
            });
        });

        blockReportManager.getBlockNameToProps().forEach((blockName, blockProps) -> {
            int defaultStateId = blockProps.getDefaultState();
            BlockReportManager.InternalBlockState defaultState = blockReportManager.getBlockIdToBlockState().get(defaultStateId);
            Map<Integer, Map<String, String>> blockIds = blockReportManager.getBlockIdToBlockState().entrySet().stream()
                .filter((e) -> e.getValue().getBlockName().equals(blockName))
                .collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue().getProperties()));

            Set<String> propNames = new HashSet<>(blockProps.getValidProps().keySet());
            List<PropInterfaceData> propInterfaces = new ArrayList<>();

            allPropInterfaces.forEach((propInterface) -> {
                Map<Boolean, Set<String>> propNamesByRequired = propInterface.getPropReportMappings().stream()
                    .collect(Collectors.groupingBy(PropReportMapping::isRequired, Collectors.mapping(PropReportMapping::getPropName, Collectors.toSet())));
                if (propNames.containsAll(propNamesByRequired.get(true))) {
                    Set<String> allPropNames = propNamesByRequired.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
                    boolean valid = propInterface.getPropReportMappings().stream()
                        .allMatch((mapping) -> {
                            try {
                                if (!mapping.isRequired() && !blockProps.getValidProps().containsKey(mapping.getPropName())) {
                                    return true;
                                }
                                String defaultStateValue = blockIds.get(defaultStateId).get(mapping.getPropName());
                                String[] validValues = Iterables.toArray(blockProps.getValidProps().get(mapping.getPropName()), String.class);
                                return canInstantiateReport(mapping.getReportType(), defaultStateValue, validValues);
                            } catch (Exception e) {
                                throw new RuntimeException("Could not map prop " + mapping.getPropName() + " for block " + blockName, e);
                            }
                        });
                    if (valid) {
                        propNames.removeAll(allPropNames);
                        propInterfaces.add(propInterface);
                    }
                }
            });

            if (!propNames.isEmpty()) {
                throw new IllegalStateException("Unmapped properties found for " + blockName + ": " + String.join(", ", propNames));
            }

            Material material = Material.matchMaterial(blockName);

            String blockDataClassName;
            if (BlockData.class.isAssignableFrom(material.data)) {
                blockDataClassName = "Glow" + material.data.getSimpleName() + "BlockData";
            } else {
                List<String> interfaceNames = propInterfaces.stream()
                    .map((data) -> data.getInterfaceClass().getSimpleName())
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
                    .flatMap((propInterfaceData) -> {
                        if (polyfillData.containsKey(propInterfaceData.getInterfaceClass())) {
                            Collection<PropPolyfillData> polyfills = polyfillData.get(propInterfaceData.getInterfaceClass());
                            List<TypeName> polyfillTypeNames = new ArrayList<>();
                            for (PropPolyfillData polyfill : polyfills) {
                                if (polyfill.getInterfaceClass().isAssignableFrom(material.data)) {
                                    polyfillTypeNames.add(TypeName.get(polyfill.getAssociatedInterface()));
                                }
                            }
                            if (polyfillTypeNames.isEmpty()) {
                                return Stream.of(TypeName.get(propInterfaceData.getAssociatedInterface()));
                            }
                            return polyfillTypeNames.stream();
                        }
                        return Stream.of(TypeName.get(propInterfaceData.getAssociatedInterface()));
                    })
                    .sorted(Comparator.comparing(TypeName::toString))
                    .distinct()
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
                        ParameterSpec.builder(mapStringStateValueTypeName, "stateValues", Modifier.FINAL).build(),
                        ParameterSpec.builder(TypeName.BOOLEAN, "explicit", Modifier.FINAL).build()
                    ))
                    .addStatement("super(material, stateValues, explicit)")
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
                    .addStatement("return new $T(material, stateValues, explicit)", generatedClassName)
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

    private Set<String> implementBlockManagers(IngestionResult ingestionResult, Set<ManagerBlockDataDetails> managerBlockDataDetails) {
        Map<String, List<MethodSpec>> blockManagersAndConstructors = new HashMap<>();

        managerBlockDataDetails.forEach((detail) -> {
            List<CodeBlock> individualBlockDataConstructorBlocks = new ArrayList<>();
            individualBlockDataConstructorBlocks.add(
                CodeBlock.of(
                    "BlockDataConstructor.Builder builder= BlockDataConstructor.builder($T.$L, $T::new)",
                    Material.class,
                    detail.getMaterial(),
                    ClassName.get(ingestionResult.getBlockDataImplPackage(), detail.getBlockDataSimpleName())
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

            blockManagersAndConstructors
                .computeIfAbsent(
                    detail.getBlockDataSimpleName() + "Manager",
                    (k) -> new ArrayList<>()
                )
                .add(
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

        blockManagersAndConstructors.forEach(
            (className, individualBlockConstructors) -> {
                MethodSpec createBlockDataConstructors = MethodSpec.methodBuilder("createBlockDataConstructors")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
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
                    .addModifiers(Modifier.PRIVATE)
                    .build();

                TypeSpec blockDataManagerTypeSpec =  TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethods(individualBlockConstructors)
                    .addMethod(createBlockDataConstructors)
                    .addMethod(constructor)
                    .build();

                JavaFile blockDataManagerJavaFile = JavaFile.builder(ingestionResult.getBlockDataBlockManagerPackage(), blockDataManagerTypeSpec)
                    .indent("    ")
                    .build();

                try {
                    blockDataManagerJavaFile.writeTo(filer);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        );

        return blockManagersAndConstructors.keySet();
    }

    private void implementManager(IngestionResult ingestionResult, Set<String> managerClassNames) {
        String blockDataManagerPackage = ingestionResult.getBlockDataManagerPackage();

        ParameterizedTypeName blockDataConstructorsTypeName = ParameterizedTypeName.get(
            ClassName.get(Set.class),
            ClassName.get(BlockDataConstructor.class)
        );

        MethodSpec createBlockDataConstructors = MethodSpec.methodBuilder("createBlockDataConstructors")
            .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
            .returns(blockDataConstructorsTypeName)
            .addStatement("final $T blockDataConstructors = new $T<>()", blockDataConstructorsTypeName, HashSet.class)
            .addCode(
                managerClassNames.stream()
                    .map((className) -> CodeBlock.of("blockDataConstructors.addAll($T.createBlockDataConstructors())", ClassName.get(ingestionResult.getBlockDataBlockManagerPackage(), className)))
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

    private static boolean canInstantiateReport(Class<? extends StateReport<?>> stateReportType, String defaultValue, String[] validValues) {
        Constructor<? extends StateReport<?>> constructor;
        try {
            constructor = stateReportType.getConstructor(String.class, String[].class);
        } catch(NoSuchMethodException e) {
            throw new IllegalStateException("StateReport " + stateReportType + " does not have a valid constructor", e);
        }
        try {
            constructor.newInstance(defaultValue, validValues);
            return true;
        } catch (IllegalAccessException|InstantiationException e) {
            throw new IllegalStateException("Could not instantiate report", e);
        } catch (InvocationTargetException e) {
            return false;
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
