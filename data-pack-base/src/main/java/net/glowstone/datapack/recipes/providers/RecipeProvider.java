package net.glowstone.datapack.recipes.providers;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Optional;
import java.util.stream.Stream;

public interface RecipeProvider<I extends Inventory> {
    NamespacedKey getKey();
    Class<I> getInventoryClass();
    Optional<Recipe> getRecipeFor(I inventory);
    Stream<Recipe> getRecipesForResult(ItemStack result);
    default Optional<Recipe> getRecipeGeneric(Inventory inventory) {
        return this.getRecipeFor((I) inventory);
    }
}
