package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Function;

public abstract class CraftingRecipeInput implements RecipeInput {
    private final ItemStack[] input;

    protected CraftingRecipeInput(ItemStack[] input) {
        this.input = input;
    }

    protected CraftingRecipeInput(CraftingInventory inventory) {
        this(inventory.getMatrix());
    }

    public ItemStack[] getInput() {
        return input;
    }

    protected abstract static class CraftingRecipeInputFactory<T extends CraftingRecipeInput> implements InventoryRecipeInputFactory<T>, InventoryTypeRecipeInputFactory<T> {
        private final Function<ItemStack[], T> itemStackConstructor;

        protected CraftingRecipeInputFactory(Function<ItemStack[], T> itemStackConstructor) {
            this.itemStackConstructor = itemStackConstructor;
        }

        @Override
        public Optional<T> create(Inventory inventory) {
            if (inventory instanceof CraftingInventory) {
                return Optional.of(this.itemStackConstructor.apply(((CraftingInventory) inventory).getMatrix()));
            }
            return Optional.empty();
        }

        @Override
        public Optional<T> create(InventoryType inventoryType, ItemStack[] itemStacks) {
            switch (inventoryType) {
                case WORKBENCH:
                case CRAFTING:
                    return Optional.of(this.itemStackConstructor.apply(itemStacks));

                default:
                    return Optional.empty();
            }
        }
    }
}
