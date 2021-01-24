package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.providers.RecipeProvider.RecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.SpecialRecipeProvider.SpecialRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.StaticRecipeProvider.StaticRecipeProviderFactory;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class RecipeProviderRegistry {
    private static final Set<RecipeProviderFactory<?, ?, ?>> ALL_FACTORIES =
        ImmutableSet.of(
            ArmorDyeRecipeProvider.factory(),
            BannerAddPatternRecipeProvider.factory(),
            BannerDuplicateRecipeProvider.factory(),
            BlastingRecipeProvider.factory(),
            BookCloningRecipeProvider.factory(),
            CampfireRecipeProvider.factory(),
            FireworkRocketRecipeProvider.factory(),
            FireworkStarFadeRecipeProvider.factory(),
            FireworkStarRecipeProvider.factory(),
            FurnaceRecipeProvider.factory(),
            MapCloningRecipeProvider.factory(),
            MapExtendingRecipeProvider.factory(),
            RepairItemRecipeProvider.factory(),
            ShapedRecipeProvider.factory(),
            ShapelessRecipeProvider.factory(),
            ShieldDecorationRecipeProvider.factory(),
            ShulkerBoxColoringRecipeProvider.factory(),
            SmithingRecipeProvider.factory(),
            SmokingRecipeProvider.factory(),
            StonecuttingRecipeProvider.factory(),
            SuspiciousStewRecipeProvider.factory(),
            TippedArrowRecipeProvider.factory()
        );

    private static final Map<Class<? extends Recipe>, StaticRecipeProviderFactory<?, ?, ?, ?>> STATIC_FACTORIES_BY_MODEL_TYPE =
        ALL_FACTORIES
            .stream()
            .filter((factory) -> factory instanceof StaticRecipeProviderFactory)
            .map((factory) -> (StaticRecipeProviderFactory<?, ?, ?, ?>) factory)
            .collect(
                ImmutableMap.toImmutableMap(
                    RecipeProviderFactory::getModelType,
                    Function.identity()
                )
            );

    private static final Map<Class<? extends Recipe>, SpecialRecipeProviderFactory<?, ?, ?>> SPECIAL_FACTORIES_BY_MODEL_TYPE =
        ALL_FACTORIES
            .stream()
            .filter((factory) -> factory instanceof SpecialRecipeProviderFactory)
            .map((factory) -> (SpecialRecipeProviderFactory<?, ?, ?>) factory)
            .collect(
                ImmutableMap.toImmutableMap(
                    RecipeProviderFactory::getModelType,
                    Function.identity()
                )
            );

    private static final Map<Class<? extends org.bukkit.inventory.Recipe>, StaticRecipeProviderFactory<?, ?, ?, ?>> STATIC_FACTORIES_BY_BUKKIT_TYPE =
        ALL_FACTORIES
            .stream()
            .filter((factory) -> factory instanceof StaticRecipeProviderFactory)
            .map((factory) -> (StaticRecipeProviderFactory<?, ?, ?, ?>) factory)
            .collect(
                ImmutableMap.toImmutableMap(
                    StaticRecipeProviderFactory::getBukkitType,
                    Function.identity()
                )
            );

    public static <R extends Recipe> Optional<RecipeProviderMappingArgumentsResult> providerArguments(String namespace, String key, R recipe) {
        if (STATIC_FACTORIES_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            StaticRecipeProviderFactory<?, R, ?, ?> factory = (StaticRecipeProviderFactory<?, R, ?, ?>) STATIC_FACTORIES_BY_MODEL_TYPE.get(recipe.getClass());
            return Optional.of(new RecipeProviderMappingArgumentsResult(factory.getRecipeProviderType(), factory.providerArguments(namespace, key, recipe)));
        }

        if (SPECIAL_FACTORIES_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            SpecialRecipeProviderFactory<?, R, ?> factory = (SpecialRecipeProviderFactory<?, R, ?>) SPECIAL_FACTORIES_BY_MODEL_TYPE.get(recipe.getClass());
            return Optional.of(new RecipeProviderMappingArgumentsResult(factory.getRecipeProviderType(), factory.providerArguments(namespace, key)));
        }

        return Optional.empty();
    }

    public static <R extends Recipe> Optional<RecipeProvider<?, ?>> provider(TagManager tagManager, String namespace, String key, R recipe) {
        if (STATIC_FACTORIES_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            StaticRecipeProviderFactory<?, R, ?, ?> factory = (StaticRecipeProviderFactory<?, R, ?, ?>) STATIC_FACTORIES_BY_MODEL_TYPE.get(recipe.getClass());
            return Optional.of(factory.provider(tagManager, namespace, key, recipe));
        }

        if (SPECIAL_FACTORIES_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            SpecialRecipeProviderFactory<?, R, ?> factory = (SpecialRecipeProviderFactory<?, R, ?>) SPECIAL_FACTORIES_BY_MODEL_TYPE.get(recipe.getClass());
            return Optional.of(factory.provider(namespace, key));
        }

        return Optional.empty();
    }

    public static Optional<StaticRecipeProvider<?, ?, ?>> provider(org.bukkit.inventory.Recipe recipe) {
        if (STATIC_FACTORIES_BY_BUKKIT_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            StaticRecipeProviderFactory<?, ?, ?, ?> mapping =
                STATIC_FACTORIES_BY_BUKKIT_TYPE.get(recipe.getClass());
            return Optional.of(mapping.providerGeneric(recipe));
        }

        return Optional.empty();
    }

    public static class RecipeProviderMappingArgumentsResult {
        private final Class<? extends RecipeProvider<?, ?>> recipeProviderType;
        private final List<MappingArgument> mappingArguments;

        public RecipeProviderMappingArgumentsResult(Class<? extends RecipeProvider<?, ?>> recipeProviderType, List<MappingArgument> mappingArguments) {
            this.recipeProviderType = recipeProviderType;
            this.mappingArguments = mappingArguments;
        }

        public Class<? extends RecipeProvider<?, ?>> getRecipeProviderType() {
            return recipeProviderType;
        }

        public List<MappingArgument> getMappingArguments() {
            return mappingArguments;
        }
    }
}
