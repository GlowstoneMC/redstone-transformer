package net.glowstone.datapack.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Optional;

public class CookingRecipeProvider<T extends CookingRecipe<T>> extends AbstractRecipeProvider {
    private final T recipe;

    public CookingRecipeProvider(String namespace,
                                 String key,
                                 Material resultMaterial,
                                 int resultAmount,
                                 MaterialTagRecipeChoice choice,
                                 float experience,
                                 int cookingTime,
                                 CookingRecipeConstructor<T> constructor) {
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
    public Optional<Recipe> getRecipeFor(ItemStack... items) {
        if (items.length != 1) {
            throw new IllegalArgumentException("Cooking recipes only support 1 input.");
        }
        if (matchesWildcard(recipe.getInput(), items[0])) {
            return Optional.of(recipe);
        }
        return Optional.empty();
    }
    
    @FunctionalInterface
    public interface CookingRecipeConstructor<T extends CookingRecipe<T>> {
        T create(NamespacedKey key, ItemStack result, RecipeChoice input, float experience, int cookingTime);
    }
}
