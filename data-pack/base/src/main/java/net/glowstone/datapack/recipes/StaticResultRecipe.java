package net.glowstone.datapack.recipes;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class StaticResultRecipe implements Recipe, Keyed {
    private final NamespacedKey key;
    private final ItemStack result;

    public StaticResultRecipe(NamespacedKey key, ItemStack result) {
        this.key = key;
        this.result = result;
    }

    @Override
    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }
}
