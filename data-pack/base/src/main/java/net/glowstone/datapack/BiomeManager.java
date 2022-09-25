package net.glowstone.datapack;

import net.glowstone.datapack.loader.model.external.worldgen.biome.BiomeDef;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;

public interface BiomeManager {
    int getId(Biome biome);

    Biome getBiome(int id);

    BiomeDef getBiomeDef(Biome biome);
}
