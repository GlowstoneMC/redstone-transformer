package net.glowstone.datapack.recipes.providers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class AbstractRecipeProvider<I extends Inventory> implements RecipeProvider<I> {
    private final NamespacedKey key;
    private final Class<I> inventoryClass;

    protected AbstractRecipeProvider(NamespacedKey key, Class<I> inventoryClass) {
        this.key = key;
        this.inventoryClass = inventoryClass;
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    @Override
    public Class<I> getInventoryClass() {
        return this.inventoryClass;
    }

    private boolean isWildcard(short data) {
        // old-style wildcards (byte -1) not supported
        return data == Short.MAX_VALUE;
    }

    protected boolean matchesWildcard(ItemStack expected, ItemStack actual) {
        return itemStackIsEmpty(actual) && expected.getType() == actual.getType() && (
            isWildcard(expected.getDurability()) || expected.getDurability() == actual
                .getDurability());
    }

    /**
     * Checks whether the given ItemStack is empty.
     *
     * @param stack the ItemStack to check
     * @return whether the given ItemStack is empty
     */
    protected static boolean itemStackIsEmpty(@Nullable ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0;
    }
}
