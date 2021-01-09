package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class FurnaceRecipeInput extends CookingRecipeInput {
    public static Optional<FurnaceRecipeInput> create(Inventory inventory) {
        if (inventory instanceof FurnaceInventory) {
            return Optional.of(new FurnaceRecipeInput((FurnaceInventory) inventory));
        }
        return Optional.empty();
    }

    public static Optional<FurnaceRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        if (itemStacks.length != 1) {
            return Optional.empty();
        }

        switch (inventoryType) {
            case FURNACE:
                return Optional.of(new FurnaceRecipeInput(itemStacks[0]));

            default:
                return Optional.empty();
        }
    }

    public FurnaceRecipeInput(FurnaceInventory inventory) {
        super(inventory);
    }

    public FurnaceRecipeInput(ItemStack input) {
        super(input);
    }
}
