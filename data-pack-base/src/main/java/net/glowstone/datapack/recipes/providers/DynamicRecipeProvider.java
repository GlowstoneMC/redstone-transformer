package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.RecipeInput;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.stream.Stream;

public abstract class DynamicRecipeProvider<I extends RecipeInput> extends AbstractRecipeProvider<I> {
    protected DynamicRecipeProvider(Class<I> inventoryClass, NamespacedKey key) {
        super(inventoryClass, key);
    }

    @Override
    public Stream<Recipe> getRecipesForResult(ItemStack result) {
        return Stream.empty();
    }
}
