package net.glowstone.datapack.recipes.providers.mapping;

import net.glowstone.datapack.loader.model.external.recipe.BlastingRecipe;
import net.glowstone.datapack.recipes.providers.BlastingRecipeProvider;
import net.glowstone.datapack.recipes.inputs.BlastingRecipeInput;

public class BlastingRecipeProviderMapping extends CookingRecipeProviderMapping<BlastingRecipe,
                                                                                org.bukkit.inventory.BlastingRecipe,
                                                                                BlastingRecipeInput,
                                                                                BlastingRecipeProvider> {
    public BlastingRecipeProviderMapping() {
        super(
            BlastingRecipe.class,
            BlastingRecipeProvider.class,
            org.bukkit.inventory.BlastingRecipe.class,
            org.bukkit.inventory.BlastingRecipe::new,
            BlastingRecipeProvider::new,
            BlastingRecipeProvider::new);
    }
}
