package net.glowstone.processor.block.data.ingestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;

import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.ProcessorConfiguration;
import net.glowstone.processor.block.data.annotations.PropPolyfill;
import org.bukkit.block.data.BlockData;

public class AnnotatedElementIngester {
    private final Set<? extends Element> annotatedElements;

    public AnnotatedElementIngester(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.annotatedElements = annotations.stream()
            .flatMap((a) -> roundEnv.getElementsAnnotatedWith(a).stream())
            .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }

    public boolean hasElementsToIngest() {
        return !annotatedElements.isEmpty();
    }

    public IngestionResult ingestElements() {
        final List<PropInterfaceData> propInterfaces = new ArrayList<>();
        final List<PropPolyfillData> propPolyfills = new ArrayList<>();
        Optional<ProcessorConfiguration> processorConfiguration = Optional.empty();

        for (Element e : annotatedElements) {
            AssociatedWithProps propsAnnotation = e.getAnnotation(AssociatedWithProps.class);
            PropPolyfill polyfillAnnotation = e.getAnnotation(PropPolyfill.class);
            ProcessorConfiguration processorConfigAnnotation = e.getAnnotation(ProcessorConfiguration.class);

            if (propsAnnotation != null) {
                if (e.getKind() == ElementKind.INTERFACE) {
                    TypeElement type = (TypeElement) e;

                    Set<PropReportMapping> propReportMappings = Arrays.stream(propsAnnotation.props())
                        .map((prop) -> new PropReportMapping(prop.propName(), getClassFrom(prop::reportType), prop.required()))
                        .collect(Collectors.toSet());

                    Class<? extends BlockData> interfaceClass = getClassFrom(propsAnnotation::interfaceClass);

                    PropInterfaceData data = new PropInterfaceData(
                        propReportMappings,
                        type.asType(),
                        interfaceClass
                    );
                    propInterfaces.add(data);
                }
            } else if (polyfillAnnotation != null) {
                if (e.getKind() == ElementKind.INTERFACE) {
                    TypeElement type = (TypeElement) e;
                    List<Class<? extends BlockData>> replacesInterfaces = getClassesFrom(polyfillAnnotation::replaces);
                    Class<? extends BlockData> interfaceClass = getClassFrom(polyfillAnnotation::interfaceClass);
                    PropPolyfillData data = new PropPolyfillData(
                        type.asType(),
                        replacesInterfaces,
                        interfaceClass
                    );
                    propPolyfills.add(data);
                }
            } else if (processorConfigAnnotation != null) {
                if (processorConfiguration.isPresent()) {
                    throw new IllegalStateException("Cannot specify multiple ProcssorConfigurations.");
                }

                processorConfiguration = Optional.of(processorConfigAnnotation);
            }
        }

        if (!processorConfiguration.isPresent()) {
            throw new IllegalStateException("No processor configuration found.");
        }

        return new IngestionResult(
            Collections.unmodifiableList(propInterfaces),
            Collections.unmodifiableList(propPolyfills),
            processorConfiguration.get().blockDataManagerPackage(),
            processorConfiguration.get().blockDataImplPackage()
        );
    }

    private Optional<DeclaredType> getDeclaredTypeFrom(Supplier<Class<?>> classSupplier) {
        try {
            classSupplier.get();
            return Optional.empty();
        } catch (MirroredTypeException e) {
            return Optional.of((DeclaredType) e.getTypeMirror());
        }
    }

    private List<DeclaredType> getDeclaredTypesFrom(Supplier<Class<?>[]> classesSupplier) {
        try {
            classesSupplier.get();
            return Collections.emptyList();
        } catch (MirroredTypesException e) {
            return e.getTypeMirrors()
                .stream()
                .map((typeMirror) -> (DeclaredType)typeMirror)
                .collect(Collectors.toList());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Class<? extends T> getClassOfType(String className) {
        try {
            return (Class<? extends T>) Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Could not find class " + className);
        }
    }

    private <T> Class<? extends T> getClassFrom(DeclaredType declaredType) {
        TypeElement typeElement = (TypeElement) declaredType.asElement();
        String qualifiedName = typeElement.getQualifiedName().toString();
        return getClassOfType(qualifiedName);
    }

    private <T> Class<? extends T> getClassFrom(Supplier<Class<?>> classSupplier) {
        DeclaredType declaredType = getDeclaredTypeFrom(classSupplier)
            .orElseThrow(() -> new IllegalStateException("No state value type found."));
        return getClassFrom(declaredType);
    }
    
    private <T> List<Class<? extends T>> getClassesFrom(Supplier<Class<?>[]> classesSupplier) {
        List<DeclaredType> declaredTypes = getDeclaredTypesFrom(classesSupplier);
        return declaredTypes.stream()
            .map(this::<T>getClassFrom)
            .collect(Collectors.toList());
    }
}
