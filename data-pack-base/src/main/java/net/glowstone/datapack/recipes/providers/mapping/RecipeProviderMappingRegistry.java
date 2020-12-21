package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class RecipeProviderMappingRegistry {
    private static final Map<Class<? extends Recipe>, RecipeProviderMapping<?, ?>> RECIPE_PROVIDER_MAPPINGS =
        Stream
            .<RecipeProviderMapping<?, ?>>of(
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

    public static <R extends Recipe> Optional<RecipeProviderMappingArgumentsResult> providerArguments(String namespace, String key, R recipe) {
        if (!RECIPE_PROVIDER_MAPPINGS.containsKey(recipe.getClass())) {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        RecipeProviderMapping<?, R> mapping = (RecipeProviderMapping<?, R>) RECIPE_PROVIDER_MAPPINGS.get(recipe.getClass());
        return Optional.of(new RecipeProviderMappingArgumentsResult(mapping.getRecipeProviderType(), mapping.providerArguments(namespace, key, recipe)));
    }

    public static <R extends Recipe> Optional<RecipeProvider<?>> provider(TagManager tagManager, String namespace, String key, R recipe) {
        if (!RECIPE_PROVIDER_MAPPINGS.containsKey(recipe.getClass())) {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        RecipeProviderMapping<?, R> mapping = (RecipeProviderMapping<?, R>) RECIPE_PROVIDER_MAPPINGS.get(recipe.getClass());
        return Optional.of(mapping.provider(tagManager, namespace, key, recipe));
    }

    public static class RecipeProviderMappingArgumentsResult {
        private final Class<? extends RecipeProvider<?>> recipeProviderType;
        private final List<MappingArgument> mappingArguments;

        public RecipeProviderMappingArgumentsResult(Class<? extends RecipeProvider<?>> recipeProviderType, List<MappingArgument> mappingArguments) {
            this.recipeProviderType = recipeProviderType;
            this.mappingArguments = mappingArguments;
        }

        public Class<? extends RecipeProvider<?>> getRecipeProviderType() {
            return recipeProviderType;
        }

        public List<MappingArgument> getMappingArguments() {
            return mappingArguments;
        }
    }
}
