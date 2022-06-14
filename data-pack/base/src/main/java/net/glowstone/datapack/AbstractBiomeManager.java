package net.glowstone.datapack;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.glowstone.datapack.loader.model.external.worldgen.biome.BiomeDef;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;

public class AbstractBiomeManager implements BiomeManager {

    private final BiMap<Biome, Integer> biomeBiMap;
    private final BiMap<Biome, Class<? extends BiomeDef>> biomeDataBiMap;

    public AbstractBiomeManager() {
        this.biomeBiMap = HashBiMap.create();
        this.biomeDataBiMap = HashBiMap.create();
        loadDefaul
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
    public boolean addBiome(NamespacedKey biome, int id) {
        return false;
    }

    @Override
    public boolean removeBiome(NamespacedKey biome) {
        return false;
    }

    @Override
    public boolean removeBiome(int id) {
        return false;
    }

    protected abstract List<> defaultBiomeDefs();

    @Override
    public void reset() {

    }

    @Override
    public void clear() {

    }

    private void loadDefaultRecipes() {
        defaultBiomeDefs()
                .forEach((provider) -> {
                    this.recipesByKey.put(provider.getKey(), provider);
                    this.recipesByInputType.put(provider.getFactory().getInputClass(), provider);
                });
    }
}
