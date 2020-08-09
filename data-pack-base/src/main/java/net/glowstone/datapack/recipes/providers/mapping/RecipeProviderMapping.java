package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public interface RecipeProviderMapping<P extends RecipeProvider<?>, R extends Recipe> {
    Class<R> getModelType();
    Class<P> getRecipeProviderType();
    List<MappingArgument> providerArguments(String namespace, String key, R recipe);
    P provider(AbstractTagManager tagManager, String namespace, String key, R recipe);
}
