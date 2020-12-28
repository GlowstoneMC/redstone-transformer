package net.glowstone.datapack.recipes.providers.mapping;

import net.glowstone.datapack.loader.model.external.recipe.SmeltingRecipe;
import net.glowstone.datapack.recipes.providers.FurnaceRecipeProvider;
import net.glowstone.datapack.recipes.inputs.FurnaceRecipeInput;

public class FurnaceRecipeProviderMapping extends CookingRecipeProviderMapping<SmeltingRecipe,
                                                                               org.bukkit.inventory.FurnaceRecipe,
                                                                               FurnaceRecipeInput,
                                                                               FurnaceRecipeProvider> {
    public FurnaceRecipeProviderMapping() {
        super(
            SmeltingRecipe.class,
            FurnaceRecipeProvider.class,
            org.bukkit.inventory.FurnaceRecipe.class,
            org.bukkit.inventory.FurnaceRecipe::new,
            FurnaceRecipeProvider::new,
            FurnaceRecipeProvider::new
        );
    }
}
