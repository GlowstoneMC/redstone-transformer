package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkRocketRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkStarRecipe;
import net.glowstone.datapack.recipes.providers.FireworkRocketRecipeProvider;
import net.glowstone.datapack.recipes.providers.FireworkStarRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class FireworkStarRecipeProviderMapping implements RecipeProviderMapping<FireworkStarRecipeProvider, FireworkStarRecipe> {
    @Override
    public Class<FireworkStarRecipe> getModelType() {
        return FireworkStarRecipe.class;
    }

    @Override
    public Class<FireworkStarRecipeProvider> getRecipeProviderType() {
        return FireworkStarRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, FireworkStarRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public FireworkStarRecipeProvider provider(TagManager tagManager, String namespace, String key, FireworkStarRecipe recipe) {
        return new FireworkStarRecipeProvider(namespace, key);
    }
}
