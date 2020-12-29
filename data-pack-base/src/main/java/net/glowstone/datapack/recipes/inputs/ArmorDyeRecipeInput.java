package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ArmorDyeRecipeInput extends CraftingRecipeInput {
    public static Optional<ArmorDyeRecipeInput> create(Inventory inventory) {
        return create(ArmorDyeRecipeInput::new, inventory);
    }

    public static Optional<ArmorDyeRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(ArmorDyeRecipeInput::new, inventoryType, itemStacks);
    }

    public ArmorDyeRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public ArmorDyeRecipeInput(ItemStack[] input) {
        super(input);
    }
}
