package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class RepairItemRecipeInput extends CraftingRecipeInput {
    public static Optional<RepairItemRecipeInput> create(Inventory inventory) {
        if (inventory instanceof CraftingRecipeInput) {
            return Optional.of(new RepairItemRecipeInput((CraftingInventory) inventory));
        }
        return Optional.empty();
    }

    public static Optional<RepairItemRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        switch (inventoryType) {
            case WORKBENCH:
            case CRAFTING:
                return Optional.of(new RepairItemRecipeInput(itemStacks));

            default:
                return Optional.empty();
        }
    }

    public RepairItemRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public RepairItemRecipeInput(ItemStack[] input) {
        super(input);
    }
}
