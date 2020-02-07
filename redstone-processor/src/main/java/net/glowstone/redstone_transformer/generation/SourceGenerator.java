package net.glowstone.redstone_transformer.generation;

import com.squareup.javapoet.ClassName;
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
import java.util.stream.Collectors;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import net.glowstone.block.data.AbstractBlockDataManager;
import net.glowstone.block.data.BlockDataConstructor;
import net.glowstone.block.data.states.AbstractStatefulBlockData;
import net.glowstone.block.data.states.StateValue;
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

            String blockDataClassName = propInterfaces.stream()
                .map(PropInterfaceData::getInterfaceName)
                .sorted()
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
            managerBlockDataDetails.add(new ManagerBlockDataDetails(
                materialName,
                blockDataClassName,
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

        ParameterizedTypeName blockDataConstructorsTypeName = ParameterizedTypeName.get(
            ClassName.get(Set.class),
            ClassName.get(BlockDataConstructor.class)
        );

        MethodSpec.Builder createBlockDataConstructorsBuilder = MethodSpec.methodBuilder("createBlockDataConstructors")
            .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
            .returns(blockDataConstructorsTypeName)
            .addStatement("$T blockDataConstructors = new $T<>()", blockDataConstructorsTypeName, HashSet.class);

        managerBlockDataDetails.forEach((detail) -> {
            StringBuilder statement = new StringBuilder("blockDataConstructors.add(new $T($T.$L, $T::new, createMap(");
            List<Object> args = new ArrayList<>(Arrays.asList(
                BlockDataConstructor.class,
                Material.class,
                detail.getMaterialName(),
                ClassName.get(blockDataImplPackage, detail.getBlockDataSimpleName())));
            detail.getStateReportDetails().forEach((stateReportDetail) -> {
                args.addAll(Arrays.asList(
                    stateReportDetail.getPropName(),
                    stateReportDetail.getStateReportType(),
                    stateReportDetail.getDefaultValue()
                ));
                statement.append("createEntry($S, new $T($S, createSet(");
                args.addAll(stateReportDetail.getValidValues());
                statement.append(stateReportDetail.getValidValues().stream().map(x -> "$S").collect(Collectors.joining(", ")));
                statement.append(")))");
            });
            statement.append(")))");

            createBlockDataConstructorsBuilder.addStatement(statement.toString(), args.toArray(new Object[]{}));
        });

        MethodSpec createBlockDataConstructors = createBlockDataConstructorsBuilder
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
            .build();

        try {
            blockDataManagerJavaFile.writeTo(filer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
