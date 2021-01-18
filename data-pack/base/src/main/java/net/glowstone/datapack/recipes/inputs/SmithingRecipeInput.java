package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.StonecutterInventory;

import java.util.Optional;

public class SmithingRecipeInput implements RecipeInput {
    public static Optional<SmithingRecipeInput> create(Inventory inventory) {
        if (inventory instanceof SmithingInventory) {
            return Optional.of(new SmithingRecipeInput((SmithingInventory) inventory));
        }
        return Optional.empty();
    }

    public static Optional<SmithingRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        if (itemStacks.length != 2) {
            return Optional.empty();
        }

        switch (inventoryType) {
            case SMITHING:
                return Optional.of(new SmithingRecipeInput(itemStacks[0], itemStacks[1]));

            default:
                return Optional.empty();
        }
    }

    private final ItemStack inputEquipment;
    private final ItemStack inputMineral;

    public SmithingRecipeInput(SmithingInventory inventory) {
        this(inventory.getInputEquipment(), inventory.getInputMineral());
    }

    public SmithingRecipeInput(ItemStack inputEquipment, ItemStack inputMineral) {
        this.inputEquipment = inputEquipment;
        this.inputMineral = inputMineral;
    }

    public ItemStack getInputEquipment() {
        return inputEquipment;
    }

    public ItemStack getInputMineral() {
        return this.inputMineral;
    }
}
