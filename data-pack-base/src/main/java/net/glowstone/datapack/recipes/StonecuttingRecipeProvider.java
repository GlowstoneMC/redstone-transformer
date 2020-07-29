package net.glowstone.datapack.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.StonecuttingRecipe;

import java.util.Optional;

public class StonecuttingRecipeProvider extends AbstractRecipeProvider {
    private final StonecuttingRecipe recipe;

    public StonecuttingRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount, MaterialTagRecipeChoice source) {
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
    public Optional<Recipe> getRecipeFor(ItemStack... items) {
        if (items.length != 1) {
            throw new IllegalArgumentException("Cooking recipes only support 1 input.");
        }
        if (matchesWildcard(recipe.getInput(), items[0])) {
            return Optional.of(recipe);
        }
        return Optional.empty();
    }
}
