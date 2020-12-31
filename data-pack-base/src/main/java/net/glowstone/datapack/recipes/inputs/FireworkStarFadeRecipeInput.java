package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class FireworkStarFadeRecipeInput extends CraftingRecipeInput {
    public static Optional<FireworkStarFadeRecipeInput> create(Inventory inventory) {
        return create(FireworkStarFadeRecipeInput::new, inventory);
    }

    public static Optional<FireworkStarFadeRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(FireworkStarFadeRecipeInput::new, inventoryType, itemStacks);
    }

    public FireworkStarFadeRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public FireworkStarFadeRecipeInput(ItemStack[] input) {
        super(input);
    }
}
