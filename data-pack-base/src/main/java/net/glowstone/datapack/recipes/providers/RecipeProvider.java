package net.glowstone.datapack.recipes.providers;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Recipe;

import java.util.Optional;

public interface RecipeProvider<I extends Inventory> {
    NamespacedKey getKey();
    Class<I> getInventoryClass();
    Optional<Recipe> getRecipeFor(I inventory);
    default Optional<Recipe> getRecipeGeneric(Inventory inventory) {
        return this.getRecipeFor((I) inventory);
    }
}
