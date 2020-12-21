package net.glowstone.datapack.recipes.providers.mapping;

import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public interface RecipeProviderMapping<P extends RecipeProvider<?>, R extends Recipe> {
    Class<R> getModelType();
    Class<P> getRecipeProviderType();
    List<MappingArgument> providerArguments(String namespace, String key, R recipe);
    P provider(TagManager tagManager, String namespace, String key, R recipe);
}
