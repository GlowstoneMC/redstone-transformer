package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.recipes.inputs.ShapelessRecipeInput;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ShapelessRecipeProvider extends StaticRecipeProvider<ShapelessRecipe, ShapelessRecipeInput> {
    public ShapelessRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount, Optional<String> group, List<RecipeChoice> ingredients) {
        this(
            new ShapelessRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount)
            )
        );
        ShapelessRecipe recipe = getRecipe();
        group.ifPresent(recipe::setGroup);
        ingredients.forEach(recipe::addIngredient);
    }

    public ShapelessRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount) {
        this(
            new ShapelessRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount)
            )
        );
    }

    public ShapelessRecipeProvider(ShapelessRecipe recipe) {
        super(
            ShapelessRecipeInput.class,
            recipe
        );
    }

    public ShapelessRecipeProvider setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    public ShapelessRecipeProvider addIngredient(MaterialTagRecipeChoice choice) {
        getRecipe().addIngredient(choice);
        return this;
    }

    @Override
    public Optional<Recipe> getRecipeFor(ShapelessRecipeInput input) {
        boolean[] accountedFor = new boolean[input.getInput().length];

        // Mark empty item slots accounted for
        for (int i = 0; i < input.getInput().length; ++i) {
            accountedFor[i] = itemStackIsEmpty(input.getInput()[i]);
        }

        // Make sure each ingredient in the recipe exists in the inventory
        for (ItemStack ingredient : getRecipe().getIngredientList()) {
            boolean foundItem = false;
            for (int i = 0; i < input.getInput().length; ++i) {
                // if this item is not already used and it matches this ingredient...
                if (!accountedFor[i] && matchesWildcard(ingredient, input.getInput()[i])) {
                    // ... this item is accounted for and this ingredient is found.
                    accountedFor[i] = foundItem = true;
                }
            }
            // no item matched this ingredient, so the recipe fails
            if (!foundItem) {
                return Optional.empty();
            }
        }

        // Make sure inventory has no leftover items
        for (int i = 0; i < input.getInput().length; ++i) {
            if (!accountedFor[i]) {
                return Optional.empty();
            }
        }

        return Optional.of(getRecipe());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRecipe().getKey(),
            getRecipe().getResult(),
            getRecipe().getChoiceList(),
            getRecipe().getGroup()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapelessRecipeProvider that = (ShapelessRecipeProvider) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getChoiceList(), that.getRecipe().getChoiceList())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }
}
