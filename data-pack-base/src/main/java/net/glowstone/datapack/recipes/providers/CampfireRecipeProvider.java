package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.CampfireRecipeInput;
import org.bukkit.Material;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Optional;

public class CampfireRecipeProvider extends CookingRecipeProvider<CampfireRecipe, CampfireRecipeInput> {
    public CampfireRecipeProvider(String namespace,
                                  String key,
                                  Material resultMaterial,
                                  int resultAmount,
                                  Optional<String> group,
                                  RecipeChoice choice,
                                  float experience,
                                  int cookingTime,
                                  CookingRecipeConstructor<CampfireRecipe> constructor) {
        super(CampfireRecipeInput.class, namespace, key, resultMaterial, resultAmount, group, choice, experience, cookingTime, constructor);
    }

    public CampfireRecipeProvider(CampfireRecipe recipe) {
        super(CampfireRecipeInput.class, recipe);
    }
}
