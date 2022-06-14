package net.glowstone.datapack.loader.model.external.worldgen;

import net.glowstone.datapack.loader.DataPackLoader;
import net.glowstone.datapack.loader.model.external.worldgen.biome.BiomeDef;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class WorldGen {
    private final Map<String, BiomeDef> biomes;

    private static Map<String, BiomeDef> loadBiomes(Path worldgenPath, DataPackLoader loader) throws IOException {
        Map<String, BiomeDef> biomes = new HashMap<>();

        Path biomesPath = worldgenPath.resolve("biomes");
        for (Map.Entry<String, Path> resource : loader.iterateResourceFiles(biomesPath, ".json")) {
            try {
                biomes.put(resource.getKey(), loader.getObjectMapper().readValue(Files.newInputStream(resource.getValue()), BiomeDef.class));
            } catch (IOException e) {
                throw new IOException("Could not parse " + resource.getValue(), e);
            }
        }

        return biomes;
    }

    public static WorldGen loadWorldGenWithName(Path namespacePath, DataPackLoader loader) throws IOException {
        Path worldGenPath = namespacePath.resolve("worldgen");
        return new WorldGen(
            loadBiomes(worldGenPath, loader)
        );
    }

    public WorldGen(Map<String, BiomeDef> biomes) {
        this.biomes = biomes;
    }

    public Map<String, BiomeDef> getBiomes() {
        return biomes;
    }
}
