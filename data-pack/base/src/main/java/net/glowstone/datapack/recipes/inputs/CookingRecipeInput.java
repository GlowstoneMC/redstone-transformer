package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

public abstract class CookingRecipeInput implements RecipeInput {
    private final ItemStack input;

    protected CookingRecipeInput(ItemStack input) {
        this.input = input;
    }

    protected CookingRecipeInput(FurnaceInventory inventory) {
        this(inventory.getSmelting());
    }

    public ItemStack getInput() {
        return input;
    }
}
