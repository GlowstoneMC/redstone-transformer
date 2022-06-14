package net.glowstone.datapack.loader;

import net.glowstone.datapack.loader.model.external.DataPack;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataPackLoaderTest {
    @Test
    void loadPacks() throws Exception {
        URL dataPacksURL = getClass().getResource(String.format("/%s/data-packs", System.getProperty("vanilla.package.namespace")));
        URI dataPacksURI = dataPacksURL.toURI();
        // Create filesystem
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        try {
            FileSystems.newFileSystem(dataPacksURI, env);
        } catch (Exception ignored) {
        }
        // Query filesystem
        Path dataPacksPath = Paths.get(dataPacksURI);

        Map<String, DataPack> dataPacks = new DataPackLoader().loadPacks(dataPacksPath);

        assertEquals(1, dataPacks.size());
    }
}