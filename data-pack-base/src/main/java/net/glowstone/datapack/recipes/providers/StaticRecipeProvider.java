package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.RecipeInput;
import org.bukkit.Keyed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.stream.Stream;

public abstract class StaticRecipeProvider<R extends Recipe & Keyed, I extends RecipeInput> extends AbstractRecipeProvider<I> {
    private R recipe;

    protected StaticRecipeProvider(Class<I> inventoryClass, R recipe) {
        super(inventoryClass, recipe.getKey());
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
