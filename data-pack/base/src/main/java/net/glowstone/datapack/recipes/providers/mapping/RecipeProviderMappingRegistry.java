package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Keyed;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class RecipeProviderMappingRegistry {
    private static final List<StaticRecipeProviderMapping<?, ?, ?>> STATIC_RECIPE_PROVIDER_MAPPINGS =
        ImmutableList.of(
            new BlastingRecipeProviderMapping(),
            new CampfireRecipeProviderMapping(),
            new FurnaceRecipeProviderMapping(),
            new SmokingRecipeProviderMapping(),
            new ShapelessRecipeProviderMapping(),
            new ShapedRecipeProviderMapping(),
            new StonecuttingRecipeProviderMapping()
        );

    private static final List<RecipeProviderMapping<?, ?>> RECIPE_PROVIDER_MAPPINGS =
        ImmutableList.<RecipeProviderMapping<?, ?>>builder()
            .addAll(STATIC_RECIPE_PROVIDER_MAPPINGS)
            .add(
                new ArmorDyeRecipeProviderMapping(),
                new BannerAddPatternRecipeProviderMapping(),
                new BannerDuplicateRecipeProviderMapping(),
                new BookCloningRecipeProviderMapping(),
                new FireworkRocketRecipeProviderMapping(),
                new FireworkStarFadeRecipeProviderMapping(),
                new FireworkStarRecipeProviderMapping(),
                new MapCloningRecipeProviderMapping(),
                new MapExtendingRecipeProviderMapping(),
                new RepairItemRecipeProviderMapping(),
                new ShieldDecorationRecipeProviderMapping(),
                new ShulkerBoxColoringRecipeProviderMapping(),
                new SuspiciousStewRecipeProviderMapping(),
                new TippedArrowRecipeProviderMapping()
            )
            .build();

    private static final Map<Class<? extends Recipe>, RecipeProviderMapping<?, ?>> MAPPINGS_BY_MODEL_TYPE =
        RECIPE_PROVIDER_MAPPINGS
            .stream()
            .collect(
                ImmutableMap.toImmutableMap(
                    RecipeProviderMapping::getModelType,
                    Function.identity()
                )
            );

    private static final Map<Class<? extends org.bukkit.inventory.Recipe>, StaticRecipeProviderMapping<?, ?, ?>> STATIC_MAPPINGS_BY_BUKKIT_TYPE =
        STATIC_RECIPE_PROVIDER_MAPPINGS
            .stream()
            .collect(
                ImmutableMap.toImmutableMap(
                    StaticRecipeProviderMapping::getBukkitType,
                    Function.identity()
                )
            );

    public static <R extends Recipe> Optional<RecipeProviderMappingArgumentsResult> providerArguments(String namespace, String key, R recipe) {
        if (!MAPPINGS_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        RecipeProviderMapping<?, R> mapping = (RecipeProviderMapping<?, R>) MAPPINGS_BY_MODEL_TYPE.get(recipe.getClass());
        return Optional.of(new RecipeProviderMappingArgumentsResult(mapping.getRecipeProviderType(), mapping.providerArguments(namespace, key, recipe)));
    }

    public static <R extends Recipe> Optional<RecipeProvider<?>> provider(TagManager tagManager, String namespace, String key, R recipe) {
        if (!MAPPINGS_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        RecipeProviderMapping<?, R> mapping = (RecipeProviderMapping<?, R>) MAPPINGS_BY_MODEL_TYPE.get(recipe.getClass());
        return Optional.of(mapping.provider(tagManager, namespace, key, recipe));
    }

    public static Optional<RecipeProvider<?>> provider(org.bukkit.inventory.Recipe recipe) {
        if (!STATIC_MAPPINGS_BY_BUKKIT_TYPE.containsKey(recipe.getClass())) {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        StaticRecipeProviderMapping<RecipeProvider<?>, ?, org.bukkit.inventory.Recipe> mapping =
            (StaticRecipeProviderMapping<RecipeProvider<?>, ?, org.bukkit.inventory.Recipe>) STATIC_MAPPINGS_BY_BUKKIT_TYPE.get(recipe.getClass());
        return Optional.of(mapping.provider(recipe));
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
