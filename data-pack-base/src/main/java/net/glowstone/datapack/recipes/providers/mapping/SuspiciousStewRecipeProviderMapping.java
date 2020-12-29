package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.SuspiciousStewRecipe;
import net.glowstone.datapack.recipes.providers.SuspiciousStewRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class SuspiciousStewRecipeProviderMapping implements RecipeProviderMapping<SuspiciousStewRecipeProvider, SuspiciousStewRecipe> {
    @Override
    public Class<SuspiciousStewRecipe> getModelType() {
        return SuspiciousStewRecipe.class;
    }

    @Override
    public Class<SuspiciousStewRecipeProvider> getRecipeProviderType() {
        return SuspiciousStewRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, SuspiciousStewRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public SuspiciousStewRecipeProvider provider(TagManager tagManager, String namespace, String key, SuspiciousStewRecipe recipe) {
        return new SuspiciousStewRecipeProvider(namespace, key);
    }
}
