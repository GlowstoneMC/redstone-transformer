package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Function;

public abstract class CookingRecipeInput implements RecipeInput {
    private final ItemStack input;

    protected CookingRecipeInput(ItemStack input) {
        this.input = input;
    }

    public ItemStack getInput() {
        return input;
    }

    protected abstract static class CookingRecipeInputFactory<T extends CookingRecipeInput> implements InventoryTypeRecipeInputFactory<T> {
        private final InventoryType inventoryType;
        private final Function<ItemStack, T> itemStackConstructor;

        protected CookingRecipeInputFactory(InventoryType inventoryType, Function<ItemStack, T> itemStackConstructor) {
            this.inventoryType = inventoryType;
            this.itemStackConstructor = itemStackConstructor;
        }

        @Override
        public Optional<T> create(InventoryType inventoryType, ItemStack[] itemStacks) {
            if (this.inventoryType == inventoryType) {
                return Optional.of(itemStackConstructor.apply(itemStacks[0]));
            }

            return Optional.empty();
        }
    }
}
