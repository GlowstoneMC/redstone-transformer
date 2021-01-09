package net.glowstone.datapack.recipes.providers.mapping;

import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public interface RecipeProviderMapping<ProviderType extends RecipeProvider<?>, ExternalType extends Recipe> {
    Class<ExternalType> getModelType();
    Class<ProviderType> getRecipeProviderType();
    List<MappingArgument> providerArguments(String namespace, String key, ExternalType recipe);
    ProviderType provider(TagManager tagManager, String namespace, String key, ExternalType recipe);
}
