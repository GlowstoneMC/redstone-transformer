package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class RepairItemRecipeInput extends CraftingRecipeInput {
    public static Optional<RepairItemRecipeInput> create(Inventory inventory) {
        return create(RepairItemRecipeInput::new, inventory);
    }

    public static Optional<RepairItemRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(RepairItemRecipeInput::new, inventoryType, itemStacks);
    }

    public RepairItemRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public RepairItemRecipeInput(ItemStack[] input) {
        super(input);
    }
}
