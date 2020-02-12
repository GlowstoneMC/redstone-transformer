package net.glowstone.processor.block.data;

import com.google.auto.service.AutoService;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import net.glowstone.processor.block.data.generation.SourceGenerator;
import net.glowstone.processor.block.data.ingestion.AnnotatedElementIngester;
import net.glowstone.processor.block.data.ingestion.IngestionResult;
import net.glowstone.processor.block.data.loaders.BlockReportLoader;
import net.glowstone.processor.block.data.report.BlockReportManager;

@SupportedAnnotationTypes({
    "net.glowstone.processor.block.data.annotations.AssociatedWithProps",
    "net.glowstone.processor.block.data.annotations.ProcessorConfiguration"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class BlockReportProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        AnnotatedElementIngester ingester = new AnnotatedElementIngester(annotations, roundEnv);
        BlockReportLoader loader = new BlockReportLoader(processingEnv);
        SourceGenerator generator = new SourceGenerator(processingEnv);
        if (ingester.hasElementsToIngest()) {
            IngestionResult ingestionResult = ingester.ingestElements();
            BlockReportManager blockReportManager = loader.loadBlockReports();
            generator.generateSources(ingestionResult, blockReportManager);
        }
        return true;
    }

}
