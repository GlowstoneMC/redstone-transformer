package net.glowstone.datapack.recipes.providers.mapping;

import net.glowstone.datapack.loader.model.external.recipe.CampfireCookingRecipe;
import net.glowstone.datapack.recipes.providers.CampfireRecipeProvider;
import net.glowstone.datapack.recipes.inputs.CampfireRecipeInput;

public class CampfireRecipeProviderMapping extends CookingRecipeProviderMapping<CampfireCookingRecipe,
                                                                                org.bukkit.inventory.CampfireRecipe,
                                                                                CampfireRecipeInput,
                                                                                CampfireRecipeProvider> {
    public CampfireRecipeProviderMapping() {
        super(
            CampfireCookingRecipe.class,
            CampfireRecipeProvider.class,
            org.bukkit.inventory.CampfireRecipe.class,
            org.bukkit.inventory.CampfireRecipe::new,
            CampfireRecipeProvider::new,
            CampfireRecipeProvider::new
        );
    }
}
