package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkRocketRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.MapCloningRecipe;
import net.glowstone.datapack.recipes.providers.FireworkRocketRecipeProvider;
import net.glowstone.datapack.recipes.providers.MapCloningRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class FireworkRocketRecipeProviderMapping implements RecipeProviderMapping<FireworkRocketRecipeProvider, FireworkRocketRecipe> {
    @Override
    public Class<FireworkRocketRecipe> getModelType() {
        return FireworkRocketRecipe.class;
    }

    @Override
    public Class<FireworkRocketRecipeProvider> getRecipeProviderType() {
        return FireworkRocketRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, FireworkRocketRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public FireworkRocketRecipeProvider provider(TagManager tagManager, String namespace, String key, FireworkRocketRecipe recipe) {
        return new FireworkRocketRecipeProvider(namespace, key);
    }
}
