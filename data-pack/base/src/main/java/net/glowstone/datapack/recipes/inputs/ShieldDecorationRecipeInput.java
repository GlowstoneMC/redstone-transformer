package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ShieldDecorationRecipeInput extends CraftingRecipeInput {
    public static Optional<ShieldDecorationRecipeInput> create(Inventory inventory) {
        return create(ShieldDecorationRecipeInput::new, inventory);
    }

    public static Optional<ShieldDecorationRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(ShieldDecorationRecipeInput::new, inventoryType, itemStacks);
    }

    public ShieldDecorationRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public ShieldDecorationRecipeInput(ItemStack[] input) {
        super(input);
    }
}
