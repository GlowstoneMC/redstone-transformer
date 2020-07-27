package net.glowstone.datapack.recipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Optional;

public class CookingRecipeProvider extends AbstractRecipeProvider {
    private final CookingRecipe<?> recipe;

    public CookingRecipeProvider(CookingRecipe<?> recipe) {
        this.recipe = recipe;
    }

    @Override
    public NamespacedKey getKey() {
        return recipe.getKey();
    }

    @Override
    public Optional<Recipe> getRecipeFor(ItemStack... items) {
        if (items.length != 1) {
            throw new IllegalArgumentException("Cooking recipes only support 1 input.");
        }
        if (matchesWildcard(recipe.getInput(), items[0])) {
            return Optional.of(recipe);
        }
        return Optional.empty();
    }
}
