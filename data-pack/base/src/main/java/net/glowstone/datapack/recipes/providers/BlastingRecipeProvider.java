package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.BlastingRecipeInput;
import org.bukkit.Material;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Optional;

public class BlastingRecipeProvider extends CookingRecipeProvider<BlastingRecipe, BlastingRecipeInput> {
    public BlastingRecipeProvider(String namespace,
                                  String key,
                                  Material resultMaterial,
                                  int resultAmount,
                                  Optional<String> group,
                                  RecipeChoice choice,
                                  float experience,
                                  int cookingTime,
                                  CookingRecipeConstructor<BlastingRecipe> constructor) {
        super(BlastingRecipeInput.class, namespace, key, resultMaterial, resultAmount, group, choice, experience, cookingTime, constructor);
    }

    public BlastingRecipeProvider(BlastingRecipe recipe) {
        super(BlastingRecipeInput.class, recipe);
    }
}
