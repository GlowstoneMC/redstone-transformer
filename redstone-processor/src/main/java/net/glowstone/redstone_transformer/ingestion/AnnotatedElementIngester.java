package net.glowstone.redstone_transformer.ingestion;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
import net.glowstone.block.data.states.reports.StateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProp;
import net.glowstone.redstone_transformer.annotations.ProcessorConfiguration;

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
        final Map<String, PropInterfaceData> propInterfaces = new HashMap<>();
        Optional<ProcessorConfiguration> processorConfiguration = Optional.empty();

        for (Element e : annotatedElements) {
            AssociatedWithProp propAnnotation = e.getAnnotation(AssociatedWithProp.class);
            ProcessorConfiguration processorConfigAnnotation = e.getAnnotation(ProcessorConfiguration.class);

            if (propAnnotation != null) {
                if (e.getKind() == ElementKind.INTERFACE) {
                    TypeElement type = (TypeElement) e;
                    String typeName = type.getQualifiedName().toString();

                    DeclaredType stateReportDeclared = getDeclaredTypeFrom(propAnnotation::reportType)
                        .orElseThrow(() -> new IllegalStateException("No state value type found for " + typeName));
                    TypeElement stateReportTypeElement = (TypeElement) stateReportDeclared.asElement();
                    String stateReportTypeName = stateReportTypeElement.getQualifiedName().toString();

                    Class<? extends StateReport<?>> stateReportClass = getClassOfType(stateReportTypeName);

                    PropInterfaceData data = new PropInterfaceData(
                        propAnnotation.propName(),
                        type.asType(),
                        stateReportClass,
                        propAnnotation.interfaceName()
                    );
                    propInterfaces.put(data.getPropName(), data);
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
            Collections.unmodifiableMap(propInterfaces),
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

    @SuppressWarnings("unchecked")
    private <T> Class<? extends T> getClassOfType(String className) {
        try {
            return (Class<? extends T>) Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Could not find class " + className);
        }
    }
}
