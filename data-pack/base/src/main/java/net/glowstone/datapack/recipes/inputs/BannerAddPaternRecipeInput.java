package net.glowstone.datapack.recipes.inputs;

import java.util.Optional;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BannerAddPaternRecipeInput extends CraftingRecipeInput {
    public static Optional<BannerAddPaternRecipeInput> create(Inventory inventory) {
        return create(BannerAddPaternRecipeInput::new, inventory);
    }

    public static Optional<BannerAddPaternRecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks) {
        return create(BannerAddPaternRecipeInput::new, inventoryType, itemStacks);
    }

    public BannerAddPaternRecipeInput(CraftingInventory inventory) {
        super(inventory);
    }

    public BannerAddPaternRecipeInput(ItemStack[] input) {
        super(input);
    }
}
