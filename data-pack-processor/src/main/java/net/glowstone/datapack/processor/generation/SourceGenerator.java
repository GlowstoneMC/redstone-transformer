package net.glowstone.datapack.processor.generation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.loader.model.external.tag.Tag;
import net.glowstone.datapack.processor.generation.recipes.RecipeManagerGenerator;
import net.glowstone.datapack.processor.generation.tags.TagManagerGenerator;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
                ImmutableMap.<String, Tag>builder()
                    .put("coals", new Tag(false, Lists.newArrayList("minecraft:coal", "minecraft:charcoal")))
                    .build(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap()
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
            );

        dataPack.getNamespacedData().forEach(
            (namespaceName, data) ->
                generators.forEach(
                    (generator) ->
                        generator.addItems(namespaceName, data)
                )
        );

        missingData.forEach(
            (namespaceName, data) ->
                generators.forEach(
                    (generator) ->
                        generator.addItems(namespaceName, data)
                )
        );

        generators.forEach((generator) -> generator.generateManager(generatedClassPath, generatedClassNamespace));
    }
}
