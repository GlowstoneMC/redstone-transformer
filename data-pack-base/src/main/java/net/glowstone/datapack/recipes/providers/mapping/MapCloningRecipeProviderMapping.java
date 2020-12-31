package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.MapCloningRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.MapExtendingRecipe;
import net.glowstone.datapack.recipes.providers.MapCloningRecipeProvider;
import net.glowstone.datapack.recipes.providers.MapExtendingRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class MapCloningRecipeProviderMapping implements RecipeProviderMapping<MapCloningRecipeProvider, MapCloningRecipe> {
    @Override
    public Class<MapCloningRecipe> getModelType() {
        return MapCloningRecipe.class;
    }

    @Override
    public Class<MapCloningRecipeProvider> getRecipeProviderType() {
        return MapCloningRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, MapCloningRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public MapCloningRecipeProvider provider(TagManager tagManager, String namespace, String key, MapCloningRecipe recipe) {
        return new MapCloningRecipeProvider(namespace, key);
    }
}
