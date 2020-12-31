package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkStarFadeRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkStarRecipe;
import net.glowstone.datapack.recipes.providers.FireworkStarFadeRecipeProvider;
import net.glowstone.datapack.recipes.providers.FireworkStarRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class FireworkStarFadeRecipeProviderMapping implements RecipeProviderMapping<FireworkStarFadeRecipeProvider, FireworkStarFadeRecipe> {
    @Override
    public Class<FireworkStarFadeRecipe> getModelType() {
        return FireworkStarFadeRecipe.class;
    }

    @Override
    public Class<FireworkStarFadeRecipeProvider> getRecipeProviderType() {
        return FireworkStarFadeRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, FireworkStarFadeRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public FireworkStarFadeRecipeProvider provider(TagManager tagManager, String namespace, String key, FireworkStarFadeRecipe recipe) {
        return new FireworkStarFadeRecipeProvider(namespace, key);
    }
}
