package net.glowstone.datapack.recipes;

import org.bukkit.inventory.ItemStack;

public abstract class AbstractRecipeProvider implements RecipeProvider {
    private boolean isWildcard(short data) {
        // old-style wildcards (byte -1) not supported
        return data == Short.MAX_VALUE;
    }

    protected boolean matchesWildcard(ItemStack expected, ItemStack actual) {
        return actual != null && expected.getType() == actual.getType() && (
            isWildcard(expected.getDurability()) || expected.getDurability() == actual
                .getDurability());
    }
}
