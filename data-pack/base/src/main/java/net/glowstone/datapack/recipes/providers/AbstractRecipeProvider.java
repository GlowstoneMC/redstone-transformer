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
}
