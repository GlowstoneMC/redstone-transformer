package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class BookCloningRecipeInput extends CraftingRecipeInput {
    public static Optional<BookCloningRecipeInput> create(Inventory inventory) {
        return create(BookCloningRecipeInput::new, inventory);
    }

    public static Optional<BookCloningRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(BookCloningRecipeInput::new, inventoryType, itemStacks);
    }

    public BookCloningRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public BookCloningRecipeInput(ItemStack[] input) {
        super(input);
    }
}
