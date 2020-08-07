package net.glowstone.datapack.recipes.providers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShapedRecipeProvider extends AbstractRecipeProvider<CraftingInventory> {
    private final ShapedRecipe recipe;

    public ShapedRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount, Optional<String> group, List<String> shape, Map<Character, RecipeChoice> ingredients) {
        super(CraftingInventory.class);
        this.recipe = new ShapedRecipe(new NamespacedKey(namespace, key), new ItemStack(resultMaterial, resultAmount));
        group.ifPresent(this.recipe::setGroup);
        this.recipe.shape(shape.toArray(new String[0]));
        ingredients.forEach(this.recipe::setIngredient);
    }

    public ShapedRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount) {
        super(CraftingInventory.class);
        this.recipe = new ShapedRecipe(new NamespacedKey(namespace, key), new ItemStack(resultMaterial, resultAmount));
    }

    public ShapedRecipeProvider setGroup(Optional<String> group) {
        group.ifPresent(this.recipe::setGroup);
        return this;
    }

    public ShapedRecipeProvider setGroup(String group) {
        this.recipe.setGroup(group);
        return this;
    }

    public ShapedRecipeProvider setIngredients(Map<Character, RecipeChoice> ingredients) {
        ingredients.forEach(this.recipe::setIngredient);
        return this;
    }

    public ShapedRecipeProvider setIngredient(char key, RecipeChoice ingredient) {
        this.recipe.setIngredient(key, ingredient);
        return this;
    }

    public ShapedRecipeProvider setShape(List<String> shape) {
        return setShape(shape.toArray(new String[0]));
    }

    public ShapedRecipeProvider setShape(String... shape) {
        this.recipe.shape(shape);
        return this;
    }

    @Override
    public NamespacedKey getKey() {
        return recipe.getKey();
    }

    @Override
    public Optional<Recipe> getRecipeFor(CraftingInventory inventory) {
        int size = (int) Math.sqrt(inventory.getMatrix().length);
        Map<Character, ItemStack> ingredients = recipe.getIngredientMap();
        String[] shape = recipe.getShape();

        int rows = shape.length;
        int cols = 0;
        for (String row : shape) {
            if (row.length() > cols) {
                cols = row.length();
            }
        }

        if (rows == 0 || cols == 0) {
            return Optional.empty();
        }

        // outer loop: try at each possible starting position
        for (int rowStart = 0; rowStart <= size - rows; ++rowStart) {
            for (int colStart = 0; colStart <= size - cols; ++colStart) {
                boolean skip = false;
                // inner loop: verify recipe against this position
                for (int row = 0; row < rows; ++row) {
                    for (int col = 0; col < cols; ++col) {
                        ItemStack given = inventory.getMatrix()[(rowStart + row) * size + colStart + col];
                        char ingredientChar =
                            shape[row].length() > col ? shape[row].charAt(col) : ' ';
                        ItemStack expected = ingredients.get(ingredientChar);

                        // check for mismatch in presence of an item in that slot at all
                        if (expected == null) {
                            if (itemStackIsEmpty(given)) {
                                skip = true;
                                break;
                            } else {
                                continue; // good match
                            }
                        } else if (itemStackIsEmpty(given)) {
                            skip = true;
                            break;
                        }

                        // check for type and data match
                        if (!matchesWildcard(expected, given)) {
                            skip = true;
                            break;
                        }
                    }

                    if (skip) {
                        break;
                    }
                }

                if (skip) {
                    continue;
                }

                // also check that no items outside the recipe size are present
                for (int row = 0; row < size; row++) {
                    for (int col = 0; col < size; col++) {
                        // if this position is outside the recipe and non-null, fail
                        if ((row < rowStart || row >= rowStart + rows || col < colStart
                            || col >= colStart + cols)
                            && itemStackIsEmpty(inventory.getMatrix()[row * size + col])) {
                            skip = true;
                            break;
                        }
                    }

                    if (skip) {
                        break;
                    }
                }

                if (skip) {
                    continue;
                }

                // recipe matches and zero items outside the recipe part.
                return Optional.of(recipe);
            }
        } // end position loop

        return Optional.empty();
    }
}
