package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.StonecuttingRecipe;
import net.glowstone.datapack.recipes.providers.StonecuttingRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;

import java.util.List;

public class StonecuttingRecipeProviderMapping implements RecipeProviderMapping<StonecuttingRecipeProvider, StonecuttingRecipe> {

    @Override
    public Class<StonecuttingRecipe> getModelType() {
        return StonecuttingRecipe.class;
    }

    @Override
    public Class<StonecuttingRecipeProvider> getRecipeProviderType() {
        return StonecuttingRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, StonecuttingRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key),
            MappingArgument.forEnum(Material.matchMaterial(recipe.getResult())),
            MappingArgument.forInteger(1),
            MappingArgument.forOptional(recipe.getGroup().map(MappingArgument::forString)),
            MappingUtils.generateRecipeChoiceMapping(namespace, recipe.getIngredient())
        );
    }

    @Override
    public StonecuttingRecipeProvider provider(TagManager tagManager, String namespace, String key, StonecuttingRecipe recipe) {
        return new StonecuttingRecipeProvider(
            namespace,
            key,
            Material.matchMaterial(recipe.getResult()),
            1,
            recipe.getGroup(),
            MappingUtils.generateRecipeChoice(tagManager, namespace, recipe.getIngredient())
        );
    }
}
