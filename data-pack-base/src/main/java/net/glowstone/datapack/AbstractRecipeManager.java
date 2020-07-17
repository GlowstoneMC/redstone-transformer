package net.glowstone.datapack;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRecipeManager {
    private final List<ShapedRecipe> shapedRecipes = new ArrayList<>();
    private final List<ShapelessRecipe> shapelessRecipes = new ArrayList<>();

    protected abstract List<Recipe> defaultRecipes();

    protected RecipeChoice materialChoice(Keyed... keyeds) {
        List<Material> materials = new ArrayList<>();
        for (Keyed keyed : keyeds) {
            if (keyed instanceof Material) {
                materials.add((Material) keyed);
            } else if (keyed instanceof Tag) {
                Tag<?> tag = (Tag<?>) keyed;
                for (Object tagValue : tag.getValues()) {
                    if (tagValue instanceof Material) {
                        materials.add((Material) tagValue);
                    } else {
                        throw new IllegalStateException("Tags containing non-Materials aren't supported.");
                    }
                }
            } else {
                throw new IllegalStateException("Keyed objects must be either Materials or Tags.");
            }
        }
        return new RecipeChoice.MaterialChoice(materials);
    }

    protected boolean addRecipes(List<Recipe> recipes) {
        return recipes.stream()
            .map(this::addRecipe)
            // Use reduce to avoid short circuiting, as we want to loop through all recipes.
            .reduce(true, (a, b) -> a && b);
    }

    protected boolean addRecipe(Recipe recipe) {
        if (recipe instanceof ShapedRecipe) {
            return this.addRecipe((ShapedRecipe) recipe);
        } else if (recipe instanceof ShapelessRecipe) {
            return this.addRecipe((ShapelessRecipe) recipe);
        }
        throw new IllegalArgumentException("Unknown Recipe subtype: " + recipe.getClass().getName());
    }

    protected boolean addRecipe(ShapedRecipe recipe) {
        return shapedRecipes.add(recipe);
    }

    protected boolean addRecipe(ShapelessRecipe recipe) {
        return shapelessRecipes.add(recipe);
    }
}
