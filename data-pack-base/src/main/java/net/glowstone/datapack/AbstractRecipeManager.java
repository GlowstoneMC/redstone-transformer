package net.glowstone.datapack;

import net.glowstone.datapack.recipes.RecipeProvider;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRecipeManager {
    private final Map<NamespacedKey, RecipeProvider> recipes;

    protected AbstractRecipeManager() {
        this.recipes = new HashMap<>();
        loadDefaultRecipes();
    }

    private void loadDefaultRecipes() {
        defaultRecipes()
            .forEach((provider) -> this.recipes.put(provider.getKey(), provider));
    }

    protected abstract List<RecipeProvider> defaultRecipes();

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
}
