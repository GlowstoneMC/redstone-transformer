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
    Map<Class<? extends Recipe>, RecipeProviderMapping<?, ?>> RECIPE_PROVIDER_MAPPINGS =
        Stream.<RecipeProviderMapping<?, ?>>of(
            new BlastingRecipeProviderMapping(),
            new CampfireRecipeProviderMapping(),
            new FurnaceRecipeProviderMapping(),
            new SmokingRecipeProviderMapping(),
            new ShapelessRecipeProviderMapping(),
            new ShapedRecipeProviderMapping(),
            new StonecuttingRecipeProviderMapping(),
            new ArmorDyeRecipeProviderMapping(),
            new RepairItemRecipeProviderMapping()
        )
            .collect(
                ImmutableMap.toImmutableMap(
                    RecipeProviderMapping::getModelType,
                    Function.identity()
                )
            );

    Class<R> getModelType();
    Class<P> getRecipeProviderType();
    List<MappingArgument> providerArguments(String namespace, String key, R recipe);
    P provider(AbstractTagManager tagManager, String namespace, String key, R recipe);

    @SuppressWarnings("unchecked")
    default List<MappingArgument> providerArgumentsGeneric(String namespace, String key, Recipe recipe) {
        return this.providerArguments(namespace, key, (R)recipe);
    }

    @SuppressWarnings("unchecked")
    default P providerGeneric(AbstractTagManager tagManager, String namespace, String key, Recipe recipe) {
        return this.provider(tagManager, namespace, key, (R) recipe);
    }

}
