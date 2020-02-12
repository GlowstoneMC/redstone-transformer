package net.glowstone.processor.block.data.loaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import net.glowstone.processor.block.data.report.BlockReportManager;
import net.glowstone.processor.block.data.report.BlockReportModel;

public class BlockReportLoader {
    private final Path blocksReportsPath;

    public BlockReportLoader(ProcessingEnvironment processingEnv) {
        this.blocksReportsPath = Paths.get(processingEnv.getOptions().get("reports.blocks.path"));
    }

    public BlockReportManager loadBlockReports() {
        InputStream blocksReportStream;
        try {
            blocksReportStream = Files.newInputStream(blocksReportsPath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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
}
