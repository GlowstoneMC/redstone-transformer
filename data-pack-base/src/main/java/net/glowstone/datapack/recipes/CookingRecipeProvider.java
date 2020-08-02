package net.glowstone.datapack.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Optional;
import java.util.function.Function;

public class CookingRecipeProvider<T extends CookingRecipe<T>> extends AbstractRecipeProvider<FurnaceInventory> {
    private final T recipe;

    public CookingRecipeProvider(String namespace,
                                 String key,
                                 Material resultMaterial,
                                 int resultAmount,
                                 MaterialTagRecipeChoice choice,
                                 float experience,
                                 int cookingTime,
                                 CookingRecipeConstructor<T> constructor) {
        super(FurnaceInventory.class);
        this.recipe = constructor.create(
            new NamespacedKey(namespace, key),
            new ItemStack(resultMaterial, resultAmount),
            choice,
            experience,
            cookingTime
        );
    }

    public CookingRecipeProvider<T> setGroup(String group) {
        this.recipe.setGroup(group);
        return this;
    }

    @Override
    public NamespacedKey getKey() {
        return recipe.getKey();
    }

    @Override
    public Optional<Recipe> getRecipeFor(FurnaceInventory inventory) {
        ItemStack item = inventory.getSmelting();

        if (matchesWildcard(recipe.getInput(), item)) {
            return Optional.of(recipe);
        }

        return Optional.empty();
    }
    
    @FunctionalInterface
    public interface CookingRecipeConstructor<T extends CookingRecipe<T>> {
        T create(NamespacedKey key, ItemStack result, RecipeChoice input, float experience, int cookingTime);
    }
}
