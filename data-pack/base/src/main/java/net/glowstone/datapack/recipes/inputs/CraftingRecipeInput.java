package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Function;

public abstract class CraftingRecipeInput implements RecipeInput {
    protected static <I extends CraftingRecipeInput> Optional<I> create(Function<CraftingInventory, I> constructor,
                                                                        Inventory inventory) {
        if (inventory instanceof CraftingRecipeInput) {
            return Optional.of(constructor.apply((CraftingInventory) inventory));
        }
        return Optional.empty();
    }

    protected static <I extends CraftingRecipeInput> Optional<I> create(Function<ItemStack[], I> constructor,
                                                                        InventoryType inventoryType,
                                                                        ItemStack[] itemStacks) {
        switch (inventoryType) {
            case WORKBENCH:
            case CRAFTING:
                return Optional.of(constructor.apply(itemStacks));

            default:
                return Optional.empty();
        }
    }

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
}
