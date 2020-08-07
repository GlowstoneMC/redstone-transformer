package net.glowstone.datapack.recipes.providers;

import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;

import java.util.Optional;

public class SmokingRecipeProvider extends CookingRecipeProvider<SmokingRecipe> {
    public SmokingRecipeProvider(String namespace,
                                 String key,
                                 Material resultMaterial,
                                 int resultAmount,
                                 Optional<String> group,
                                 RecipeChoice choice,
                                 float experience,
                                 int cookingTime,
                                 CookingRecipeConstructor<SmokingRecipe> constructor) {
        super(namespace, key, resultMaterial, resultAmount, group, choice, experience, cookingTime, constructor);
    }
}
