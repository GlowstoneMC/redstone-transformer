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
        URL vanillaResource = getClass().getResource(String.format("/%s/data-packs/vanilla/pack.mcmeta", System.getProperty("vanilla.package.namespace")));
        URI uri = vanillaResource.toURI();
        // Create filesystem
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        FileSystems.newFileSystem(uri, env);
        // Query filesystem
        Path vanillaPath = Paths.get(vanillaResource.toURI()).getParent().getParent();

        Map<String, DataPack> dataPacks = new DataPackLoader().loadPacks(vanillaPath);

        assertEquals(1, dataPacks.size());
    }
}