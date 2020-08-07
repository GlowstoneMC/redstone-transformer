package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.RepairItemRecipe;
import net.glowstone.datapack.recipes.providers.RepairItemRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class RepairItemRecipeProviderMapping implements RecipeProviderMapping<RepairItemRecipeProvider, RepairItemRecipe> {
    @Override
    public Class<RepairItemRecipe> getModelType() {
        return RepairItemRecipe.class;
    }

    @Override
    public Class<RepairItemRecipeProvider> getRecipeProviderType() {
        return RepairItemRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, RepairItemRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public RepairItemRecipeProvider provider(AbstractTagManager tagManager, String namespace, String key, RepairItemRecipe recipe) {
        return new RepairItemRecipeProvider(namespace, key);
    }
}
