package net.glowstone.redstone_transformer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import net.glowstone.redstone_transformer.generation.SourceGenerator;
import net.glowstone.redstone_transformer.ingestion.AnnotatedElementIngester;
import net.glowstone.redstone_transformer.ingestion.IngestionResult;
import net.glowstone.redstone_transformer.ingestion.PropInterfaceData;
import net.glowstone.redstone_transformer.loaders.BlockReportLoader;
import net.glowstone.redstone_transformer.report.BlockReportManager;
import net.glowstone.redstone_transformer.report.BlockReportModel;

@SupportedAnnotationTypes({
    "net.glowstone.redstone_transformer.annotations.AssociatedWithProp",
    "net.glowstone.redstone_transformer.annotations.ProcessorConfiguration"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class BlockReportProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        AnnotatedElementIngester ingester = new AnnotatedElementIngester(annotations, roundEnv);
        BlockReportLoader loader = new BlockReportLoader();
        SourceGenerator generator = new SourceGenerator(processingEnv);
        if (ingester.hasElementsToIngest()) {
            IngestionResult ingestionResult = ingester.ingestElements();
            BlockReportManager blockReportManager = loader.loadBlockReports();
            generator.generateSources(ingestionResult, blockReportManager);
        }
        return true;
    }

}
