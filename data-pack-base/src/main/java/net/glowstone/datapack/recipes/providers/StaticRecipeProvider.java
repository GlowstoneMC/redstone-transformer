package net.glowstone.datapack.recipes.providers;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class StaticRecipeProvider<I extends Inventory, R extends Recipe & Keyed> extends AbstractRecipeProvider<I> {
    private R recipe;

    protected StaticRecipeProvider(
        Class<I> inventoryClass,
        R recipe) {
        super(recipe.getKey(), inventoryClass);
        this.recipe = recipe;
    }

    public R getRecipe() {
        return this.recipe;
    }

    @Override
    public Stream<Recipe> getRecipesForResult(ItemStack result) {
        Recipe recipe = getRecipe();

        if (matchesWildcard(result, recipe.getResult())) {
            return Stream.of(recipe);
        }

        return Stream.empty();
    }
}
