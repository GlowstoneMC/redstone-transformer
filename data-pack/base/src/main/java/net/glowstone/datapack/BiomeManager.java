package net.glowstone.datapack;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;

public interface BiomeManager {
    int getId(Biome biome);

    Biome getBiome(int id);

    boolean addBiome(NamespacedKey biome, int id);

    boolean removeBiome(NamespacedKey biome);

    boolean removeBiome(int id);

    void reset();

    void clear();


}
