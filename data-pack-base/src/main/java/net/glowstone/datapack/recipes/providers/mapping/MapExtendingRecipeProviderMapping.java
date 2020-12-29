package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.MapExtendingRecipe;
import net.glowstone.datapack.recipes.providers.MapExtendingRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class MapExtendingRecipeProviderMapping implements RecipeProviderMapping<MapExtendingRecipeProvider, MapExtendingRecipe> {
    @Override
    public Class<MapExtendingRecipe> getModelType() {
        return MapExtendingRecipe.class;
    }

    @Override
    public Class<MapExtendingRecipeProvider> getRecipeProviderType() {
        return MapExtendingRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, MapExtendingRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public MapExtendingRecipeProvider provider(TagManager tagManager, String namespace, String key, MapExtendingRecipe recipe) {
        return new MapExtendingRecipeProvider(namespace, key);
    }
}
