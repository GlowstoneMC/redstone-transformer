package net.glowstone.datapack.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class ItemStackUtils {
    private static boolean isWildcard(short data) {
        // old-style wildcards (byte -1) not supported
        return data == Short.MAX_VALUE;
    }

    public static boolean matchesWildcard(ItemStack expected, ItemStack actual) {
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
    public static boolean itemStackIsEmpty(@Nullable ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0;
    }
}
