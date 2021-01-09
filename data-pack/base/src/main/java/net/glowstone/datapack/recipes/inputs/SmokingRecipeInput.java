package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class SmokingRecipeInput extends CookingRecipeInput {
    public static Optional<SmokingRecipeInput> create(Inventory inventory) {
        if (inventory instanceof SmokingRecipeInput) {
            return Optional.of(new SmokingRecipeInput((FurnaceInventory) inventory));
        }
        return Optional.empty();
    }

    public static Optional<SmokingRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        if (itemStacks.length != 1) {
            return Optional.empty();
        }

        switch (inventoryType) {
            case SMOKER:
                return Optional.of(new SmokingRecipeInput(itemStacks[0]));

            default:
                return Optional.empty();
        }
    }

    public SmokingRecipeInput(FurnaceInventory inventory) {
        super(inventory);
    }

    public SmokingRecipeInput(ItemStack input) {
        super(input);
    }
}
