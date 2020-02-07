package net.glowstone.redstone_transformer.loaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Map;
import net.glowstone.redstone_transformer.report.BlockReportManager;
import net.glowstone.redstone_transformer.report.BlockReportModel;

public class BlockReportLoader {
    public BlockReportManager loadBlockReports() {
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
}
