package net.glowstone.datapack.recipes.providers;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Optional;

public class FurnaceRecipeProvider extends CookingRecipeProvider<FurnaceRecipe> {
    public FurnaceRecipeProvider(String namespace,
                                 String key,
                                 Material resultMaterial,
                                 int resultAmount,
                                 Optional<String> group,
                                 RecipeChoice choice,
                                 float experience,
                                 int cookingTime,
                                 CookingRecipeConstructor<FurnaceRecipe> constructor) {
        super(namespace, key, resultMaterial, resultAmount, group, choice, experience, cookingTime, constructor);
    }
}
