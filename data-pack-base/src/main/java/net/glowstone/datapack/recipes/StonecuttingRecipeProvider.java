package net.glowstone.datapack.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.StonecutterInventory;
import org.bukkit.inventory.StonecuttingRecipe;

import java.util.Optional;

public class StonecuttingRecipeProvider extends AbstractRecipeProvider<StonecutterInventory> {
    private final StonecuttingRecipe recipe;

    public StonecuttingRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount, MaterialTagRecipeChoice source) {
        super(StonecutterInventory.class);
        this.recipe = new StonecuttingRecipe(
            new NamespacedKey(namespace, key),
            new ItemStack(resultMaterial, resultAmount),
            source
        );
    }

    public StonecuttingRecipeProvider setGroup(String group) {
        this.recipe.setGroup(group);
        return this;
    }

    @Override
    public NamespacedKey getKey() {
        return recipe.getKey();
    }

    @Override
    public Optional<Recipe> getRecipeFor(StonecutterInventory inventory) {
        ItemStack item = inventory.getItem(0);

        if (matchesWildcard(recipe.getInput(), item)) {
            return Optional.of(recipe);
        }
        return Optional.empty();
    }
}
