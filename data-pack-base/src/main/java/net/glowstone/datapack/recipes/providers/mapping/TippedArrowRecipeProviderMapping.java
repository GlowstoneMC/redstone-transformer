package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.MapExtendingRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.TippedArrowRecipe;
import net.glowstone.datapack.recipes.providers.MapExtendingRecipeProvider;
import net.glowstone.datapack.recipes.providers.TippedArrowRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class TippedArrowRecipeProviderMapping implements RecipeProviderMapping<TippedArrowRecipeProvider, TippedArrowRecipe> {
    @Override
    public Class<TippedArrowRecipe> getModelType() {
        return TippedArrowRecipe.class;
    }

    @Override
    public Class<TippedArrowRecipeProvider> getRecipeProviderType() {
        return TippedArrowRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, TippedArrowRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public TippedArrowRecipeProvider provider(TagManager tagManager, String namespace, String key, TippedArrowRecipe recipe) {
        return new TippedArrowRecipeProvider(namespace, key);
    }
}
