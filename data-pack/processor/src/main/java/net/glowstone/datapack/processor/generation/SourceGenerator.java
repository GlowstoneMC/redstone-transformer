package net.glowstone.datapack.processor.generation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.loader.model.external.worldgen.WorldGen;
import net.glowstone.datapack.processor.generation.recipes.RecipeManagerGenerator;
import net.glowstone.datapack.processor.generation.tags.TagManagerGenerator;
import net.glowstone.datapack.processor.generation.worldgen.biomes.BiomeManagerGenerator;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class SourceGenerator {
    private static final Map<String, Data> missingData = ImmutableMap.<String, Data>builder()
        .put(
            "minecraft",
            new Data(
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                new WorldGen(Collections.emptyMap())
            )
        )
        .build();

    public void generateSources(Path generatedClassPath,
                                String generatedClassNamespace,
                                DataPack dataPack) {
        List<DataPackItemSourceGenerator> generators =
            ImmutableList.of(
                new TagManagerGenerator(),
                new RecipeManagerGenerator()
                //new BiomeManagerGenerator()
            );

        Map<String, Data> mergedData = mergeMaps(
            dataPack.getNamespacedData(),
            missingData,
            (a, b) -> new Data(
                mergeMaps(a.getAdvancements(), b.getAdvancements()),
                mergeMaps(a.getFunctions(), b.getFunctions()),
                mergeMaps(a.getLootTables(), b.getLootTables()),
                mergeMaps(a.getPredicates(), b.getPredicates()),
                mergeMaps(a.getRecipes(), b.getRecipes()),
                mergeMaps(a.getStructures(), b.getStructures()),
                mergeMaps(a.getBlockTags(), b.getBlockTags()),
                mergeMaps(a.getItemTags(), b.getItemTags()),
                mergeMaps(a.getEntityTypeTags(), b.getEntityTypeTags()),
                mergeMaps(a.getFluidTags(), b.getFluidTags()),
                mergeMaps(a.getFunctionTags(), b.getFunctionTags()),
                new WorldGen(mergeMaps(a.getWorldGen().getBiomes(), b.getWorldGen().getBiomes()))
            )
        );

        DataPack unionPack = new DataPack(
            dataPack.getMcMeta(),
            mergedData
        );

        unionPack.getNamespacedData().forEach(
            (namespaceName, data) ->
                generators.forEach(
                    (generator) ->
                        generator.addItems(namespaceName, data)
                )
        );

        generators.forEach((generator) -> generator.generateManager(generatedClassPath, generatedClassNamespace));
    }

    private static <K, V> Map<K, V> mergeMaps(Map<K, V> map1, Map<K, V> map2, BiFunction<V, V, V> remappingFunc) {
        Map<K, V> merged = new HashMap<>(map1);
        map2.forEach((namespace, data) -> {
            merged.merge(namespace, data, remappingFunc);
        });
        return ImmutableMap.copyOf(merged);
    }

    private static <K, V> Map<K, V> mergeMaps(Map<K, V> map1, Map<K, V> map2) {
        return mergeMaps(map1, map2, (a, b) -> {
            throw new IllegalStateException("Found conflicts in map while merging");
        });
    }
}
