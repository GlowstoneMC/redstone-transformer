package net.glowstone.datapack.recipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Optional;

public interface RecipeProvider {
    NamespacedKey getKey();
    Optional<Recipe> getRecipeFor(ItemStack... matrix);
}
