package net.glowstone.datapack.recipes.providers;

import org.bukkit.Material;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Optional;

public class BlastingRecipeProvider extends CookingRecipeProvider<BlastingRecipe> {
    public BlastingRecipeProvider(String namespace,
                                  String key,
                                  Material resultMaterial,
                                  int resultAmount,
                                  Optional<String> group,
                                  RecipeChoice choice,
                                  float experience,
                                  int cookingTime,
                                  CookingRecipeConstructor<BlastingRecipe> constructor) {
        super(namespace, key, resultMaterial, resultAmount, group, choice, experience, cookingTime, constructor);
    }
}
