package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class TippedArrowRecipeInput extends CraftingRecipeInput {
    public static Optional<TippedArrowRecipeInput> create(Inventory inventory) {
        return create(TippedArrowRecipeInput::new, inventory);
    }

    public static Optional<TippedArrowRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(TippedArrowRecipeInput::new, inventoryType, itemStacks);
    }

    public TippedArrowRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public TippedArrowRecipeInput(ItemStack[] input) {
        super(input);
    }
}
