package net.glowstone.datapack;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import net.glowstone.datapack.loader.model.external.worldgen.biome.BiomeDef;
import org.bukkit.block.Biome;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBiomeManager implements BiomeManager {

    private final BiMap<Biome, Integer> biomeBiMap;
    private final Map<Biome, BiomeDef> biomeDefMap;

    public AbstractBiomeManager() {
        this.biomeBiMap = EnumHashBiMap.create(Biome.class);
        this.biomeDefMap = new HashMap<>();
        addDefaults();
    }

    @Override
    public int getId(Biome biome) {
        return biomeBiMap.get(biome);
    }

    @Override
    public Biome getBiome(int id) {
        return biomeBiMap.inverse().get(id);
    }

    @Override
    public BiomeDef getBiomeDef(Biome biome) {
        return biomeDefMap.get(biome);
    }

    protected void addBiome(Biome biome, int id, BiomeDef biomeDef) {
        biomeBiMap.put(biome, id);
        biomeDefMap.put(biome, biomeDef);
    }

    protected abstract void addDefaults();
}
