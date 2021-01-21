package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;

import java.util.Optional;

public class SmithingRecipeInput implements RecipeInput {
    public static SmithingRecipeInputFactory factory() {
        return SmithingRecipeInputFactory.getInstance();
    }

    private final ItemStack inputEquipment;
    private final ItemStack inputMineral;

    private SmithingRecipeInput(ItemStack inputEquipment, ItemStack inputMineral) {
        this.inputEquipment = inputEquipment;
        this.inputMineral = inputMineral;
    }

    public ItemStack getInputEquipment() {
        return inputEquipment;
    }

    public ItemStack getInputMineral() {
        return this.inputMineral;
    }

    private static class SmithingRecipeInputFactory implements InventoryRecipeInputFactory<SmithingRecipeInput>,
        InventoryTypeRecipeInputFactory<SmithingRecipeInput> {
        private static volatile SmithingRecipeInputFactory instance = null;

        private SmithingRecipeInputFactory() {
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + SmithingRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static SmithingRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (SmithingRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new SmithingRecipeInputFactory();
                    }
                }
            }
            return instance;
        }

        @Override
        public Optional<SmithingRecipeInput> create(Inventory inventory) {
            if (inventory instanceof SmithingInventory) {
                return Optional.of(new SmithingRecipeInput(((SmithingInventory) inventory).getInputEquipment(), ((SmithingInventory) inventory).getInputMineral()));
            }
            return Optional.empty();
        }

        @Override
        public Optional<SmithingRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
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
    }
}
