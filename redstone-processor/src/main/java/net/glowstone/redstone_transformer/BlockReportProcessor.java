package net.glowstone.redstone_transformer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import net.glowstone.block.data.AbstractBlockDataManager;
import net.glowstone.block.data.states.AbstractStatefulBlockData;
import net.glowstone.block.data.BlockDataConstructor;
import net.glowstone.block.data.states.StateReport;
import net.glowstone.block.data.states.StateValue;
import net.glowstone.block.data.states.StateValueReportMapper;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProp;
import net.glowstone.redstone_transformer.report.BlockReportManager;
import net.glowstone.redstone_transformer.report.BlockReportModel;
import org.bukkit.Material;

@SupportedAnnotationTypes({
    "net.glowstone.redstone_transformer.annotations.AssociatedWithProp"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class BlockReportProcessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Optional<AnnotationIngestResult> annotationIngestResult = ingestAnnotatedTypes(annotations, roundEnv);
        if (annotationIngestResult.isPresent()) {
            BlockReportManager blockReportManager = loadBlockReports();
            generateSources(annotationIngestResult.get(), blockReportManager);
        }
        return true;
    }

    private Optional<AnnotationIngestResult> ingestAnnotatedTypes(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, PropInterfaceData> propInterfaces = new HashMap<>();

        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            for (Element e : annotatedElements) {
                AssociatedWithProp propAnnotation = e.getAnnotation(AssociatedWithProp.class);

                if (e.getKind() == ElementKind.INTERFACE) {
                    TypeElement type = (TypeElement) e;
                    String typeName = type.getQualifiedName().toString();

                    DeclaredType stateValueDeclared = getDeclaredTypeFrom(propAnnotation::valueType)
                        .orElseThrow(() -> new IllegalStateException("No state value type found for " + typeName));
                    TypeElement stateValueTypeElement = (TypeElement) stateValueDeclared.asElement();
                    String stateValueTypeName = stateValueTypeElement.getQualifiedName().toString();

                    Class<? extends StateValue<?>> stateValueClass;
                    try {
                        stateValueClass = (Class<? extends StateValue<?>>) Class.forName(stateValueTypeName);
                    } catch (ClassNotFoundException ex) {
                        throw new IllegalStateException("Could not find state value class " + stateValueTypeName);
                    }

                    Class<? extends StateReport<?>> stateReportClass = StateValueReportMapper.getStateReportTypeForValueType(stateValueClass);

                    PropInterfaceData data = new PropInterfaceData(
                        propAnnotation.propName(),
                        type.asType(),
                        stateValueClass,
                        stateReportClass,
                        propAnnotation.interfaceName()
                    );
                    propInterfaces.put(data.getPropName(), data);
                }
            }
        }

        if (propInterfaces.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(new AnnotationIngestResult(
                Collections.unmodifiableMap(propInterfaces)
            ));
        }
    }

    private BlockReportManager loadBlockReports() {
        InputStream blocksReportStream = getClass().getResourceAsStream("/reports/blocks.json");
        TypeReference<Map<String, BlockReportModel>> blockReportType = new TypeReference<Map<String, BlockReportModel>>() {};
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        Map<String, BlockReportModel> blockReports;
        try {
            blockReports = objectMapper.readValue(new InputStreamReader(blocksReportStream), blockReportType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return new BlockReportManager(blockReports);
    }

    private void generateSources(AnnotationIngestResult annotationIngestResult, BlockReportManager blockReportManager) {
        Filer filer = processingEnv.getFiler();
        Set<String> createdBlockDataClasses = new HashSet<>();
        Set<ManagerBlockDataDetails> managerBlockDataDetails = new HashSet<>();
        String blockDataPackage = "net.glowstone.block.data";
        String blockDataImplPackage = blockDataPackage + ".impl";

        blockReportManager.getBlockNameToProps().forEach((blockName, blockProps) -> {

            Set<String> propNames = blockProps.getValidProps().keySet();

            List<PropInterfaceData> propInterfaces = propNames.stream()
                .map((name) -> annotationIngestResult.getPropInterfaces().get(name))
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

        JavaFile blockDataManagerJavaFile = JavaFile.builder(blockDataPackage, blockDataManagerTypeSpec)
            .build();

        try {
            blockDataManagerJavaFile.writeTo(filer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Optional<DeclaredType> getDeclaredTypeFrom(Supplier<Class<? extends Object>> classSupplier) {
        try {
            classSupplier.get();
            return Optional.empty();
        } catch (MirroredTypeException e) {
            return Optional.of((DeclaredType) e.getTypeMirror());
        }
    }

    private static class AnnotationIngestResult {
        private final Map<String, PropInterfaceData> propInterfaces;

        public AnnotationIngestResult(Map<String, PropInterfaceData> propInterfaces) {
            this.propInterfaces = propInterfaces;
        }

        public Map<String, PropInterfaceData> getPropInterfaces() {
            return propInterfaces;
        }
    }

    private static class PropInterfaceData {
        private final String propName;
        private final TypeMirror propInterface;
        private final Class<? extends StateValue<?>> valueType;
        private final Class<? extends StateReport<?>> reportType;
        private final String interfaceName;

        public PropInterfaceData(String propName, TypeMirror propInterface, Class<? extends StateValue<?>> valueType, Class<? extends StateReport<?>> reportType, String interfaceName) {
            this.propName = propName;
            this.propInterface = propInterface;
            this.valueType = valueType;
            this.reportType = reportType;
            this.interfaceName = interfaceName;
        }

        public String getPropName() {
            return propName;
        }

        public TypeMirror getPropInterface() {
            return propInterface;
        }

        public Class<? extends StateValue<?>> getValueType() {
            return valueType;
        }

        public Class<? extends StateReport<?>> getReportType() {
            return reportType;
        }

        public String getInterfaceName() {
            return interfaceName;
        }
    }

    private static class ManagerBlockDataDetails {
        private final String materialName;
        private final String blockDataSimpleName;
        private final Set<ManagerStateReportDetails> stateReportDetails;

        private ManagerBlockDataDetails(String materialName, String blockDataSimpleName, Set<ManagerStateReportDetails> stateReportDetails) {
            this.materialName = materialName;
            this.blockDataSimpleName = blockDataSimpleName;
            this.stateReportDetails = stateReportDetails;
        }

        public String getMaterialName() {
            return materialName;
        }

        public String getBlockDataSimpleName() {
            return blockDataSimpleName;
        }

        public Set<ManagerStateReportDetails> getStateReportDetails() {
            return stateReportDetails;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ManagerBlockDataDetails that = (ManagerBlockDataDetails) o;
            return Objects.equals(materialName, that.materialName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(materialName);
        }
    }

    private static class ManagerStateReportDetails {
        private final String propName;
        private final String defaultValue;
        private final Set<String> validValues;
        private final Class<? extends StateReport<?>> stateReportType;

        private ManagerStateReportDetails(String propName, String defaultValue, Set<String> validValues, Class<? extends StateReport<?>> stateReportType) {
            this.propName = propName;
            this.defaultValue = defaultValue;
            this.validValues = validValues;
            this.stateReportType = stateReportType;
        }

        public String getPropName() {
            return propName;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public Set<String> getValidValues() {
            return validValues;
        }

        public Class<? extends StateReport<?>> getStateReportType() {
            return stateReportType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ManagerStateReportDetails that = (ManagerStateReportDetails) o;
            return propName.equals(that.propName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(propName);
        }
    }
}
