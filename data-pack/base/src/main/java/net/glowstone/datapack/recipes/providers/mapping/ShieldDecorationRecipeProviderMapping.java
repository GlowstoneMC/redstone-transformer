package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.BannerDuplicateRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.ShieldDecorationRecipe;
import net.glowstone.datapack.recipes.providers.BannerDuplicateRecipeProvider;
import net.glowstone.datapack.recipes.providers.ShieldDecorationRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class ShieldDecorationRecipeProviderMapping implements RecipeProviderMapping<ShieldDecorationRecipeProvider, ShieldDecorationRecipe> {
    @Override
    public Class<ShieldDecorationRecipe> getModelType() {
        return ShieldDecorationRecipe.class;
    }

    @Override
    public Class<ShieldDecorationRecipeProvider> getRecipeProviderType() {
        return ShieldDecorationRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, ShieldDecorationRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public ShieldDecorationRecipeProvider provider(TagManager tagManager, String namespace, String key, ShieldDecorationRecipe recipe) {
        return new ShieldDecorationRecipeProvider(namespace, key);
    }
}
