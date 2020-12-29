package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class MapExtendingRecipeInput extends CraftingRecipeInput {
    public static Optional<MapExtendingRecipeInput> create(Inventory inventory) {
        return create(MapExtendingRecipeInput::new, inventory);
    }

    public static Optional<MapExtendingRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(MapExtendingRecipeInput::new, inventoryType, itemStacks);
    }

    public MapExtendingRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public MapExtendingRecipeInput(ItemStack[] input) {
        super(input);
    }
}
