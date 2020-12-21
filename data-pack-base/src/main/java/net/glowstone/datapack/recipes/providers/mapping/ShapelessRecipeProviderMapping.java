package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.ShapelessRecipe;
import net.glowstone.datapack.recipes.providers.ShapelessRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;

import java.util.List;
import java.util.stream.Collectors;

public class ShapelessRecipeProviderMapping implements RecipeProviderMapping<ShapelessRecipeProvider, ShapelessRecipe> {
    @Override
    public Class<ShapelessRecipe> getModelType() {
        return ShapelessRecipe.class;
    }

    @Override
    public Class<ShapelessRecipeProvider> getRecipeProviderType() {
        return ShapelessRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, ShapelessRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key),
            MappingArgument.forEnum(Material.matchMaterial(recipe.getResult().getItem())),
            MappingArgument.forInteger(recipe.getResult().getCount()),
            MappingArgument.forOptional(recipe.getGroup().map(MappingArgument::forString)),
            MappingArgument.forList(
                recipe.getIngredients()
                    .stream()
                    .map((items) -> MappingUtils.generateRecipeChoiceMapping(namespace, items))
                    .collect(Collectors.toList())
            )
        );
    }

    @Override
    public ShapelessRecipeProvider provider(TagManager tagManager, String namespace, String key, ShapelessRecipe recipe) {
        return new ShapelessRecipeProvider(
            namespace,
            key,
            Material.matchMaterial(recipe.getResult().getItem()),
            recipe.getResult().getCount(),
            recipe.getGroup(),
            recipe.getIngredients()
                .stream()
                .map((items) -> MappingUtils.generateRecipeChoice(tagManager, namespace, items))
                .collect(Collectors.toList())
        );
    }
}
