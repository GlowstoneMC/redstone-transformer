package net.glowstone.datapack.processor.generation;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.processor.generation.recipes.RecipeManagerGenerator;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SourceGenerator {
    public void generateSources(Path generatedClassPath,
                                String generatedClassNamespace,
                                DataPack dataPack) {
        List<DataPackItemSourceGenerator> generators =
            ImmutableList.of(
                new RecipeManagerGenerator()
            );

        Map<String, Map<String, List<String>>> namespacedTaggedItems = createNamespacedTaggedItemsMap(dataPack);

        dataPack.getNamespacedData().forEach(
            (namespaceName, data) ->
                generators.forEach(
                    (generator) ->
                        generator.addItems(namespacedTaggedItems, namespaceName, data)
                )
        );

        generators.forEach((generator) -> generator.generateManager(generatedClassPath, generatedClassNamespace));
    }

    private Map<String, Map<String, List<String>>> createNamespacedTaggedItemsMap(DataPack dataPack) {
        return dataPack.getNamespacedData()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Entry::getKey,
                (dataEntry) -> Stream.concat(
                    dataEntry.getValue()
                        .getItemTags()
                        .entrySet()
                        .stream(),
                    dataEntry.getValue()
                        .getBlockTags()
                        .entrySet()
                        .stream()
                ).collect(Collectors.toMap(
                    Entry::getKey,
                    (tagEntry) -> tagEntry.getValue().getValues()
                ))
            ));
    }
}
