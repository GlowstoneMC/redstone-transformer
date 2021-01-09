package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class BannerDuplicateRecipeInput extends CraftingRecipeInput {
    public static Optional<BannerDuplicateRecipeInput> create(Inventory inventory) {
        return create(BannerDuplicateRecipeInput::new, inventory);
    }

    public static Optional<BannerDuplicateRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(BannerDuplicateRecipeInput::new, inventoryType, itemStacks);
    }

    public BannerDuplicateRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public BannerDuplicateRecipeInput(ItemStack[] input) {
        super(input);
    }
}
