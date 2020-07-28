package net.glowstone.datapack.recipes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class StaticResultRecipe implements Recipe {
    private final ItemStack result;

    public StaticResultRecipe(ItemStack result) {
        this.result = result;
    }

    @Override
    public ItemStack getResult() {
        return this.result;
    }
}
