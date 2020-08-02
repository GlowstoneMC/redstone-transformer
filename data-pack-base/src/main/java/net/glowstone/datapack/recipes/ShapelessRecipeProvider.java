package net.glowstone.datapack.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Optional;

public class ShapelessRecipeProvider extends AbstractRecipeProvider<CraftingInventory> {
    private final ShapelessRecipe recipe;

    public ShapelessRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount) {
        super(CraftingInventory.class);
        this.recipe = new ShapelessRecipe(new NamespacedKey(namespace, key), new ItemStack(resultMaterial, resultAmount));
    }

    public ShapelessRecipeProvider setGroup(String group) {
        this.recipe.setGroup(group);
        return this;
    }

    public ShapelessRecipeProvider addIngredient(MaterialTagRecipeChoice choice) {
        this.recipe.addIngredient(choice);
        return this;
    }

    @Override
    public NamespacedKey getKey() {
        return recipe.getKey();
    }

    @Override
    public Optional<Recipe> getRecipeFor(CraftingInventory inventory) {
        boolean[] accountedFor = new boolean[inventory.getMatrix().length];

        // Mark empty item slots accounted for
        for (int i = 0; i < inventory.getMatrix().length; ++i) {
            accountedFor[i] = itemStackIsEmpty(inventory.getMatrix()[i]);
        }

        // Make sure each ingredient in the recipe exists in the inventory
        for (ItemStack ingredient : recipe.getIngredientList()) {
            boolean foundItem = false;
            for (int i = 0; i < inventory.getMatrix().length; ++i) {
                // if this item is not already used and it matches this ingredient...
                if (!accountedFor[i] && matchesWildcard(ingredient, inventory.getMatrix()[i])) {
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
        for (int i = 0; i < inventory.getMatrix().length; ++i) {
            if (!accountedFor[i]) {
                return Optional.empty();
            }
        }

        return Optional.of(recipe);
    }
}
