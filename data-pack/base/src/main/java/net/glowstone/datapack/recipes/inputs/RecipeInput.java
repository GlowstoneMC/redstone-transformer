package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface RecipeInput {
    interface RecipeInputFactory<T extends RecipeInput> {
    }

    interface InventoryRecipeInputFactory<T extends RecipeInput> extends RecipeInputFactory<T> {
        Optional<T> create(Inventory inventory);
    }

    interface InventoryTypeRecipeInputFactory<T extends RecipeInput> extends RecipeInputFactory<T> {
        Optional<T> create(InventoryType inventoryType, ItemStack[] itemStacks);
    }

    interface SingleItemRecipeInputFactory<T extends RecipeInput> extends RecipeInputFactory<T> {
        CampfireRecipeInput create(ItemStack itemStack);
    }
}
