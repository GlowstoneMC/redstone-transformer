package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.providers.ArmorDyeRecipeProvider.ArmorDyeRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.BannerAddPatternRecipeProvider.BannerAddPatternRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.BannerDuplicateRecipeProvider.BannerDuplicateRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.BlastingRecipeProvider.BlastingRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.BookCloningRecipeProvider.BookCloningRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.CampfireRecipeProvider.CampfireRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.FireworkRocketRecipeProvider.FireworkRocketRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.FireworkStarFadeRecipeProvider.FireworkStarFadeRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.FireworkStarRecipeProvider.FireworkStarRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.FurnaceRecipeProvider.FurnaceRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.MapCloningRecipeProvider.MapCloningRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.MapExtendingRecipeProvider.MapExtendingRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.RepairItemRecipeProvider.RepairItemRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.ShapedRecipeProvider.ShapedRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.ShapelessRecipeProvider.ShapelessRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.ShieldDecorationRecipeProvider.ShieldDecorationRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.ShulkerBoxColoringRecipeProvider.ShulkerBoxColoringRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.SmithingRecipeProvider.SmithingRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.SmokingRecipeProvider.SmokingRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.StonecuttingRecipeProvider.StonecuttingRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.SuspiciousStewRecipeProvider.SuspiciousStewRecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.TippedArrowRecipeProvider.TippedArrowRecipeProviderFactory;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecipeProviderRegistry {
    private static final List<RecipeProvider.RecipeProviderFactory<?, ?>> ALL_FACTORIES =
        ImmutableList.of(
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

    private static final List<StaticRecipeProvider.StaticRecipeProviderFactory<?, ?, ?>> STATIC_FACTORIES =
        ALL_FACTORIES.stream()
            .filter((factory) -> factory instanceof StaticRecipeProvider.StaticRecipeProviderFactory)
            .map((factory) -> (StaticRecipeProvider.StaticRecipeProviderFactory<?, ?, ?>) factory)
            .collect(Collectors.toList());

    private static final List<SpecialRecipeProvider.SpecialRecipeProviderFactory<?, ?>> SPECIAL_FACTORIES =
        ALL_FACTORIES.stream()
            .filter((factory) -> factory instanceof SpecialRecipeProvider.SpecialRecipeProviderFactory)
            .map((factory) -> (SpecialRecipeProvider.SpecialRecipeProviderFactory<?, ?>) factory)
            .collect(Collectors.toList());

    private static final Map<Class<? extends Recipe>, StaticRecipeProvider.StaticRecipeProviderFactory<?, ?, ?>> STATIC_FACTORIES_BY_MODEL_TYPE =
        STATIC_FACTORIES
            .stream()
            .collect(
                ImmutableMap.toImmutableMap(
                    RecipeProvider.RecipeProviderFactory::getModelType,
                    Function.identity()
                )
            );

    private static final Map<Class<? extends Recipe>, SpecialRecipeProvider.SpecialRecipeProviderFactory<?, ?>> SPECIAL_FACTORIES_BY_MODEL_TYPE =
        SPECIAL_FACTORIES
            .stream()
            .collect(
                ImmutableMap.toImmutableMap(
                    RecipeProvider.RecipeProviderFactory::getModelType,
                    Function.identity()
                )
            );

    private static final Map<Class<? extends org.bukkit.inventory.Recipe>, StaticRecipeProvider.StaticRecipeProviderFactory<?, ?, ?>> STATIC_FACTORIES_BY_BUKKIT_TYPE =
        STATIC_FACTORIES
            .stream()
            .collect(
                ImmutableMap.toImmutableMap(
                    StaticRecipeProvider.StaticRecipeProviderFactory::getBukkitType,
                    Function.identity()
                )
            );

    public static <R extends Recipe> Optional<RecipeProviderMappingArgumentsResult> providerArguments(String namespace, String key, R recipe) {
        if (STATIC_FACTORIES_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            StaticRecipeProvider.StaticRecipeProviderFactory<?, R, ?> factory = (StaticRecipeProvider.StaticRecipeProviderFactory<?, R, ?>) STATIC_FACTORIES_BY_MODEL_TYPE.get(recipe.getClass());
            return Optional.of(new RecipeProviderMappingArgumentsResult(factory.getRecipeProviderType(), factory.providerArguments(namespace, key, recipe)));
        }

        if (SPECIAL_FACTORIES_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            SpecialRecipeProvider.SpecialRecipeProviderFactory<?, R> factory = (SpecialRecipeProvider.SpecialRecipeProviderFactory<?, R>) SPECIAL_FACTORIES_BY_MODEL_TYPE.get(recipe.getClass());
            return Optional.of(new RecipeProviderMappingArgumentsResult(factory.getRecipeProviderType(), factory.providerArguments(namespace, key)));
        }

        return Optional.empty();
    }

    public static <R extends Recipe> Optional<RecipeProvider<?>> provider(TagManager tagManager, String namespace, String key, R recipe) {
        if (STATIC_FACTORIES_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            StaticRecipeProvider.StaticRecipeProviderFactory<?, R, ?> factory = (StaticRecipeProvider.StaticRecipeProviderFactory<?, R, ?>) STATIC_FACTORIES_BY_MODEL_TYPE.get(recipe.getClass());
            return Optional.of(factory.provider(tagManager, namespace, key, recipe));
        }

        if (SPECIAL_FACTORIES_BY_MODEL_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            SpecialRecipeProvider.SpecialRecipeProviderFactory<?, R> factory = (SpecialRecipeProvider.SpecialRecipeProviderFactory<?, R>) SPECIAL_FACTORIES_BY_MODEL_TYPE.get(recipe.getClass());
            return Optional.of(factory.provider(namespace, key));
        }

        return Optional.empty();
    }

    public static <R extends org.bukkit.inventory.Recipe> Optional<RecipeProvider<?>> provider(R recipe) {
        if (STATIC_FACTORIES_BY_BUKKIT_TYPE.containsKey(recipe.getClass())) {
            @SuppressWarnings("unchecked")
            StaticRecipeProvider.StaticRecipeProviderFactory<?, ?, R> mapping =
                (StaticRecipeProvider.StaticRecipeProviderFactory<?, ?, R>) STATIC_FACTORIES_BY_BUKKIT_TYPE.get(recipe.getClass());
            return Optional.of(mapping.provider(recipe));
        }

        return Optional.empty();
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
