package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.ArmorDyeRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.ShulkerBoxColoringRecipe;
import net.glowstone.datapack.recipes.providers.ArmorDyeRecipeProvider;
import net.glowstone.datapack.recipes.providers.ShulkerBoxColoringRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class ShulkerBoxColoringRecipeProviderMapping implements RecipeProviderMapping<ShulkerBoxColoringRecipeProvider, ShulkerBoxColoringRecipe> {
    @Override
    public Class<ShulkerBoxColoringRecipe> getModelType() {
        return ShulkerBoxColoringRecipe.class;
    }

    @Override
    public Class<ShulkerBoxColoringRecipeProvider> getRecipeProviderType() {
        return ShulkerBoxColoringRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, ShulkerBoxColoringRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public ShulkerBoxColoringRecipeProvider provider(TagManager tagManager, String namespace, String key, ShulkerBoxColoringRecipe recipe) {
        return new ShulkerBoxColoringRecipeProvider(namespace, key);
    }
}
