package net.glowstone.datapack.recipes.providers;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.stream.Stream;

public abstract class DynamicRecipeProvider<I extends Inventory> extends AbstractRecipeProvider<I> {
    protected DynamicRecipeProvider(NamespacedKey key, Class<I> inventoryClass) {
        super(key, inventoryClass);
    }

    @Override
    public Stream<Recipe> getRecipesForResult(ItemStack result) {
        return Stream.empty();
    }
}
