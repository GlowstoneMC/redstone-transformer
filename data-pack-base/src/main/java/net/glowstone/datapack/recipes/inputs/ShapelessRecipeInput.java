package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ShapelessRecipeInput extends CraftingRecipeInput {
    public static Optional<ShapelessRecipeInput> create(Inventory inventory) {
        if (inventory instanceof CraftingRecipeInput) {
            return Optional.of(new ShapelessRecipeInput((CraftingInventory) inventory));
        }
        return Optional.empty();
    }

    public static Optional<ShapelessRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        switch (inventoryType) {
            case WORKBENCH:
            case CRAFTING:
                return Optional.of(new ShapelessRecipeInput(itemStacks));

            default:
                return Optional.empty();
        }
    }

    public ShapelessRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public ShapelessRecipeInput(ItemStack[] input) {
        super(input);
    }
}
