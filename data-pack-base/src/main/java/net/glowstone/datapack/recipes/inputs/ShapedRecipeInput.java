package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ShapedRecipeInput extends CraftingRecipeInput {
    public static Optional<ShapedRecipeInput> create(Inventory inventory) {
        if (inventory instanceof CraftingRecipeInput) {
            return Optional.of(new ShapedRecipeInput((CraftingInventory) inventory));
        }
        return Optional.empty();
    }

    public static Optional<ShapedRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        switch (inventoryType) {
            case WORKBENCH:
            case CRAFTING:
                return Optional.of(new ShapedRecipeInput(itemStacks));

            default:
                return Optional.empty();
        }
    }

    public ShapedRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public ShapedRecipeInput(ItemStack[] input) {
        super(input);
    }
}
