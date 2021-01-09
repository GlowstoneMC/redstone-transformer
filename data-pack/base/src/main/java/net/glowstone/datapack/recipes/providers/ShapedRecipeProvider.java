package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.ShapedRecipeInput;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;
import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public class ShapedRecipeProvider extends StaticRecipeProvider<ShapedRecipe, ShapedRecipeInput> {
    public ShapedRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount, Optional<String> group, List<String> shape, Map<Character, RecipeChoice> ingredients) {
        super(
            ShapedRecipeInput.class,
            new ShapedRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount)
            )
        );
        ShapedRecipe recipe = getRecipe();
        group.ifPresent(recipe::setGroup);
        recipe.shape(shape.toArray(new String[0]));
        ingredients.forEach(recipe::setIngredient);
    }

    public ShapedRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount) {
        super(
            ShapedRecipeInput.class,
            new ShapedRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount)
            )
        );
    }

    public ShapedRecipeProvider(ShapedRecipe recipe) {
        super(ShapedRecipeInput.class, recipe);
    }

    public ShapedRecipeProvider setGroup(Optional<String> group) {
        group.ifPresent(getRecipe()::setGroup);
        return this;
    }

    public ShapedRecipeProvider setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    public ShapedRecipeProvider setIngredients(Map<Character, RecipeChoice> ingredients) {
        ingredients.forEach(getRecipe()::setIngredient);
        return this;
    }

    public ShapedRecipeProvider setIngredient(char key, RecipeChoice ingredient) {
        getRecipe().setIngredient(key, ingredient);
        return this;
    }

    public ShapedRecipeProvider setShape(List<String> shape) {
        return setShape(shape.toArray(new String[0]));
    }

    public ShapedRecipeProvider setShape(String... shape) {
        getRecipe().shape(shape);
        return this;
    }

    @Override
    public Optional<Recipe> getRecipeFor(ShapedRecipeInput input) {
        int size = (int) Math.sqrt(input.getInput().length);
        Map<Character, ItemStack> ingredients = getRecipe().getIngredientMap();
        String[] shape = getRecipe().getShape();

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
                        ItemStack given = input.getInput()[(rowStart + row) * size + colStart + col];
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
                            && itemStackIsEmpty(input.getInput()[row * size + col])) {
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
                return Optional.of(getRecipe());
            }
        } // end position loop

        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRecipe().getKey(),
            getRecipe().getResult(),
            getRecipe().getChoiceMap(),
            Arrays.hashCode(getRecipe().getShape()),
            getRecipe().getGroup()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapedRecipeProvider that = (ShapedRecipeProvider) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getChoiceMap(), that.getRecipe().getChoiceMap())
            && Arrays.equals(getRecipe().getShape(), that.getRecipe().getShape())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }
}
