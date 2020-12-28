package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.RecipeInput;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class AbstractRecipeProvider<I extends RecipeInput> implements RecipeProvider<I> {
    private final NamespacedKey key;
    private final Class<I> inputClass;

    protected AbstractRecipeProvider(Class<I> inputClass, NamespacedKey key) {
        this.key = key;
        this.inputClass = inputClass;
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    @Override
    public Class<I> getInputClass() {
        return this.inputClass;
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
