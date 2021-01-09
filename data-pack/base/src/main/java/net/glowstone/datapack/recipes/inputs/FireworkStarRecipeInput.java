package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class FireworkStarRecipeInput extends CraftingRecipeInput {
    public static Optional<FireworkStarRecipeInput> create(Inventory inventory) {
        return create(FireworkStarRecipeInput::new, inventory);
    }

    public static Optional<FireworkStarRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(FireworkStarRecipeInput::new, inventoryType, itemStacks);
    }

    public FireworkStarRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public FireworkStarRecipeInput(ItemStack[] input) {
        super(input);
    }
}
