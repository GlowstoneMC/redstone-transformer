package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class SuspiciousStewRecipeInput extends CraftingRecipeInput {
    public static Optional<SuspiciousStewRecipeInput> create(Inventory inventory) {
        return create(SuspiciousStewRecipeInput::new, inventory);
    }

    public static Optional<SuspiciousStewRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(SuspiciousStewRecipeInput::new, inventoryType, itemStacks);
    }

    public SuspiciousStewRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public SuspiciousStewRecipeInput(ItemStack[] input) {
        super(input);
    }
}
