package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ArmorDyeRecipeInput extends CraftingRecipeInput {
    public static Optional<ArmorDyeRecipeInput> create(Inventory inventory) {
        if (inventory instanceof CraftingRecipeInput) {
            return Optional.of(new ArmorDyeRecipeInput((CraftingInventory) inventory));
        }
        return Optional.empty();
    }

    public static Optional<ArmorDyeRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        switch (inventoryType) {
            case WORKBENCH:
            case CRAFTING:
                return Optional.of(new ArmorDyeRecipeInput(itemStacks));

            default:
                return Optional.empty();
        }
    }

    public ArmorDyeRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public ArmorDyeRecipeInput(ItemStack[] input) {
        super(input);
    }
}
