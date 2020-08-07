package net.glowstone.datapack.recipes.providers.mapping;

import net.glowstone.datapack.loader.model.external.recipe.CampfireCookingRecipe;
import net.glowstone.datapack.recipes.providers.CampfireRecipeProvider;

public class CampfireRecipeProviderMapping extends CookingRecipeProviderMapping<CampfireCookingRecipe, org.bukkit.inventory.CampfireRecipe, CampfireRecipeProvider> {
    public CampfireRecipeProviderMapping() {
        super(CampfireCookingRecipe.class, CampfireRecipeProvider.class, org.bukkit.inventory.CampfireRecipe.class, org.bukkit.inventory.CampfireRecipe::new, CampfireRecipeProvider::new);
    }
}
