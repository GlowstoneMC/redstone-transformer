package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public abstract class CraftingRecipeInput implements RecipeInput {
    private final ItemStack[] input;

    protected CraftingRecipeInput(ItemStack[] input) {
        this.input = input;
    }

    protected CraftingRecipeInput(CraftingInventory inventory) {
        this(inventory.getMatrix());
    }

    public ItemStack[] getInput() {
        return input;
    }
}
