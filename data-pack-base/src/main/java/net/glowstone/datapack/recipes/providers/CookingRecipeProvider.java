package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Optional;

public class CookingRecipeProvider<T extends CookingRecipe<T>> extends AbstractRecipeProvider<FurnaceInventory> {
    private final T recipe;

    public CookingRecipeProvider(String namespace,
                                 String key,
                                 Material resultMaterial,
                                 int resultAmount,
                                 RecipeChoice choice,
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

    public CookingRecipeProvider(String namespace,
                                 String key,
                                 Material resultMaterial,
                                 int resultAmount,
                                 Optional<String> group,
                                 RecipeChoice choice,
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
        group.ifPresent(this.recipe::setGroup);
    }

    public CookingRecipeProvider<T> setGroup(Optional<String> group) {
        group.ifPresent(this.recipe::setGroup);
        return this;
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
