package net.glowstone.datapack.loader;

import net.glowstone.datapack.loader.model.external.DataPack;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataPackLoaderTest {
    @Test
    @Disabled
    void loadPacks() throws Exception {
        // TODO; Must copy vanilla minecraft datapack before running this test.

        URL vanillaResource = DataPackLoaderTest.class.getResource("/data-packs/vanilla/pack.mcmeta");
        Path vanillaPath = Paths.get(vanillaResource.toURI()).getParent().getParent();

        Map<String, DataPack> dataPacks = new DataPackLoader().loadPacks(vanillaPath);

        assertEquals(1, dataPacks.size());
    }
}