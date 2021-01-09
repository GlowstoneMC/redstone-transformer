package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ShapelessRecipeInput extends CraftingRecipeInput {
    public static Optional<ShapelessRecipeInput> create(Inventory inventory) {
        return create(ShapelessRecipeInput::new, inventory);
    }

    public static Optional<ShapelessRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(ShapelessRecipeInput::new, inventoryType, itemStacks);
    }

    public ShapelessRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public ShapelessRecipeInput(ItemStack[] input) {
        super(input);
    }
}
