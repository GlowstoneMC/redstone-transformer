package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class BlastingRecipeInput extends CookingRecipeInput {
    public static Optional<BlastingRecipeInput> create(Inventory inventory) {
        if (inventory instanceof FurnaceInventory) {
            return Optional.of(new BlastingRecipeInput((FurnaceInventory) inventory));
        }
        return Optional.empty();
    }

    public static Optional<BlastingRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        if (itemStacks.length != 1) {
            return Optional.empty();
        }

        switch (inventoryType) {
            case BLAST_FURNACE:
                return Optional.of(new BlastingRecipeInput(itemStacks[0]));

            default:
                return Optional.empty();
        }
    }

    public BlastingRecipeInput(FurnaceInventory inventory) {
        super(inventory);
    }

    public BlastingRecipeInput(ItemStack input) {
        super(input);
    }
}
