package net.glowstone.datapack.recipes.providers.mapping;

import net.glowstone.datapack.loader.model.external.recipe.SmokingRecipe;
import net.glowstone.datapack.recipes.providers.SmokingRecipeProvider;

public class SmokingRecipeProviderMapping extends CookingRecipeProviderMapping<SmokingRecipe, org.bukkit.inventory.SmokingRecipe, SmokingRecipeProvider> {
    public SmokingRecipeProviderMapping() {
        super(SmokingRecipe.class, SmokingRecipeProvider.class, org.bukkit.inventory.SmokingRecipe.class, org.bukkit.inventory.SmokingRecipe::new, SmokingRecipeProvider::new);
    }
}
