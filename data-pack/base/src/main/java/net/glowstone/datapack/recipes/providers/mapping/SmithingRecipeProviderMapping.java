package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.ShapelessRecipe;
import net.glowstone.datapack.loader.model.external.recipe.SmithingRecipe;
import net.glowstone.datapack.recipes.providers.ShapelessRecipeProvider;
import net.glowstone.datapack.recipes.providers.SmithingRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SmithingRecipeProviderMapping implements StaticRecipeProviderMapping<SmithingRecipeProvider, SmithingRecipe, org.bukkit.inventory.SmithingRecipe> {
    @Override
    public Class<SmithingRecipe> getModelType() {
        return SmithingRecipe.class;
    }

    @Override
    public Class<org.bukkit.inventory.SmithingRecipe> getBukkitType() {
        return org.bukkit.inventory.SmithingRecipe.class;
    }

    @Override
    public Class<SmithingRecipeProvider> getRecipeProviderType() {
        return SmithingRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, SmithingRecipe recipe) {
        Preconditions.checkArgument(
            recipe.getResult().getItem().isPresent(),
            "SmithingRecipe must result in an item."
        );

        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key),
            MappingArgument.forEnum(Material.matchMaterial(recipe.getResult().getItem().get())),
            MappingArgument.forInteger(1),
            MappingUtils.generateRecipeChoiceMapping(namespace, Collections.singletonList(recipe.getBase())),
            MappingUtils.generateRecipeChoiceMapping(namespace, Collections.singletonList(recipe.getAddition()))
        );
    }

    @Override
    public SmithingRecipeProvider provider(TagManager tagManager, String namespace, String key, SmithingRecipe recipe) {
        Preconditions.checkArgument(
            recipe.getResult().getItem().isPresent(),
            "SmithingRecipe must result in an item."
        );

        return new SmithingRecipeProvider(
            namespace,
            key,
            Material.matchMaterial(recipe.getResult().getItem().get()),
            1,
            MappingUtils.generateRecipeChoice(tagManager, namespace, Collections.singletonList(recipe.getBase())),
            MappingUtils.generateRecipeChoice(tagManager, namespace, Collections.singletonList(recipe.getAddition()))
        );
    }

    @Override
    public SmithingRecipeProvider provider(org.bukkit.inventory.SmithingRecipe recipe) {
        return new SmithingRecipeProvider(recipe);
    }
}
