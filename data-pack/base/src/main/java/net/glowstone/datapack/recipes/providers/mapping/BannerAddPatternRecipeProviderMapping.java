package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.BannerAddPatternRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.BannerDuplicateRecipe;
import net.glowstone.datapack.recipes.providers.BannerAddPatternRecipeProvider;
import net.glowstone.datapack.recipes.providers.BannerDuplicateRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class BannerAddPatternRecipeProviderMapping implements RecipeProviderMapping<BannerAddPatternRecipeProvider, BannerAddPatternRecipe> {
    @Override
    public Class<BannerAddPatternRecipe> getModelType() {
        return BannerAddPatternRecipe.class;
    }

    @Override
    public Class<BannerAddPatternRecipeProvider> getRecipeProviderType() {
        return BannerAddPatternRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, BannerAddPatternRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public BannerAddPatternRecipeProvider provider(TagManager tagManager, String namespace, String key, BannerAddPatternRecipe recipe) {
        return new BannerAddPatternRecipeProvider(namespace, key);
    }
}
