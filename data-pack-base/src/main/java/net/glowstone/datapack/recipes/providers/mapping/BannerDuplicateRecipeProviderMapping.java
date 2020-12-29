package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.BannerDuplicateRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.BookCloningRecipe;
import net.glowstone.datapack.recipes.providers.BannerDuplicateRecipeProvider;
import net.glowstone.datapack.recipes.providers.BookCloningRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class BannerDuplicateRecipeProviderMapping implements RecipeProviderMapping<BannerDuplicateRecipeProvider, BannerDuplicateRecipe> {
    @Override
    public Class<BannerDuplicateRecipe> getModelType() {
        return BannerDuplicateRecipe.class;
    }

    @Override
    public Class<BannerDuplicateRecipeProvider> getRecipeProviderType() {
        return BannerDuplicateRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, BannerDuplicateRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public BannerDuplicateRecipeProvider provider(TagManager tagManager, String namespace, String key, BannerDuplicateRecipe recipe) {
        return new BannerDuplicateRecipeProvider(namespace, key);
    }
}
