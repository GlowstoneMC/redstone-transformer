package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.SmokingRecipeInput;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;

import java.util.Optional;

public class SmokingRecipeProvider extends CookingRecipeProvider<SmokingRecipe, SmokingRecipeInput> {
    public SmokingRecipeProvider(String namespace,
                                 String key,
                                 Material resultMaterial,
                                 int resultAmount,
                                 Optional<String> group,
                                 RecipeChoice choice,
                                 float experience,
                                 int cookingTime,
                                 CookingRecipeConstructor<SmokingRecipe> constructor) {
        super(SmokingRecipeInput.class, namespace, key, resultMaterial, resultAmount, group, choice, experience, cookingTime, constructor);
    }

    public SmokingRecipeProvider(SmokingRecipe recipe) {
        super(SmokingRecipeInput.class, recipe);
    }
}
