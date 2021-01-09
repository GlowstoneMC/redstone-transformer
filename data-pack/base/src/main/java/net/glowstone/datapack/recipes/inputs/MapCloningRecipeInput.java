package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class MapCloningRecipeInput extends CraftingRecipeInput {
    public static Optional<MapCloningRecipeInput> create(Inventory inventory) {
        return create(MapCloningRecipeInput::new, inventory);
    }

    public static Optional<MapCloningRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(MapCloningRecipeInput::new, inventoryType, itemStacks);
    }

    public MapCloningRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public MapCloningRecipeInput(ItemStack[] input) {
        super(input);
    }
}
