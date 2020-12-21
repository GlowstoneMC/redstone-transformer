package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.ShapedRecipe;
import net.glowstone.datapack.recipes.providers.ShapedRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ShapedRecipeProviderMapping implements RecipeProviderMapping<ShapedRecipeProvider, ShapedRecipe> {
    @Override
    public Class<ShapedRecipe> getModelType() {
        return ShapedRecipe.class;
    }

    @Override
    public Class<ShapedRecipeProvider> getRecipeProviderType() {
        return ShapedRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, ShapedRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key),
            MappingArgument.forEnum(Material.matchMaterial(recipe.getResult().getItem())),
            MappingArgument.forInteger(recipe.getResult().getCount()),
            MappingArgument.forOptional(recipe.getGroup().map(MappingArgument::forString)),
            MappingArgument.forList(
                recipe.getPattern()
                    .stream()
                    .map(MappingArgument::forString)
                    .collect(Collectors.toList())
            ),
            MappingArgument.forMap(
                Character.class,
                RecipeChoice.class,
                recipe.getKey()
                    .entrySet()
                    .stream()
                    .map((entry) -> new SimpleEntry<>(
                        MappingArgument.forCharacter(entry.getKey()),
                        MappingUtils.generateRecipeChoiceMapping(namespace, entry.getValue())
                    ))
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
            )
        );
    }

    @Override
    public ShapedRecipeProvider provider(TagManager tagManager, String namespace, String key, ShapedRecipe recipe) {
        return new ShapedRecipeProvider(
            namespace,
            key,
            Material.matchMaterial(recipe.getResult().getItem()),
            recipe.getResult().getCount(),
            recipe.getGroup(),
            recipe.getPattern(),
            recipe.getKey()
                .entrySet()
                .stream()
                .map((entry) -> new SimpleEntry<>(entry.getKey(), MappingUtils.generateRecipeChoice(tagManager, namespace, entry.getValue())))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
        );
    }
}
