package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecutterInventory;

import java.util.Optional;

public class StonecuttingRecipeInput extends CookingRecipeInput {
    public static Optional<StonecuttingRecipeInput> create(Inventory inventory) {
        if (inventory instanceof StonecutterInventory) {
            return Optional.of(new StonecuttingRecipeInput((StonecutterInventory) inventory));
        }
        return Optional.empty();
    }

    public static Optional<StonecuttingRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        if (itemStacks.length != 1) {
            return Optional.empty();
        }

        switch (inventoryType) {
            case STONECUTTER:
                return Optional.of(new StonecuttingRecipeInput(itemStacks[0]));

            default:
                return Optional.empty();
        }
    }

    public StonecuttingRecipeInput(StonecutterInventory inventory) {
        this(inventory.getItem(0));
    }

    public StonecuttingRecipeInput(ItemStack input) {
        super(input);
    }
}
