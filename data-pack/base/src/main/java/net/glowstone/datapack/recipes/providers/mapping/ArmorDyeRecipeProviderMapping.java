package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.ArmorDyeRecipe;
import net.glowstone.datapack.recipes.providers.ArmorDyeRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class ArmorDyeRecipeProviderMapping implements RecipeProviderMapping<ArmorDyeRecipeProvider, ArmorDyeRecipe> {
    @Override
    public Class<ArmorDyeRecipe> getModelType() {
        return ArmorDyeRecipe.class;
    }

    @Override
    public Class<ArmorDyeRecipeProvider> getRecipeProviderType() {
        return ArmorDyeRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, ArmorDyeRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public ArmorDyeRecipeProvider provider(TagManager tagManager, String namespace, String key, ArmorDyeRecipe recipe) {
        return new ArmorDyeRecipeProvider(namespace, key);
    }
}
