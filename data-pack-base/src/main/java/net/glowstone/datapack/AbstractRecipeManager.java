package net.glowstone.datapack;

import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.recipes.RecipeProvider;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractRecipeManager {
    private final Map<NamespacedKey, RecipeProvider> recipes;
    protected final AbstractTagManager tagManager;

    protected AbstractRecipeManager(AbstractTagManager tagManager) {
        this.recipes = new HashMap<>();
        this.tagManager = tagManager;
        loadDefaultRecipes();
    }

    private void loadDefaultRecipes() {
        defaultRecipes()
            .forEach((provider) -> this.recipes.put(provider.getKey(), provider));
    }

    protected abstract List<RecipeProvider> defaultRecipes();

    protected MaterialTagRecipeChoice materialChoice(Keyed... keyeds) {
        Set<Material> values = new HashSet<>();
        Set<SubTagTrackingTag<Material>> tags = new HashSet<>();
        for (Keyed keyed : keyeds) {
            if (keyed instanceof Material) {
                values.add((Material) keyed);
            } else if (keyed instanceof SubTagTrackingTag) {
                SubTagTrackingTag<?> tag = (SubTagTrackingTag<?>) keyed;
                if (Material.class.isAssignableFrom(tag.getValueClass())) {
                    @SuppressWarnings("unchecked")
                    SubTagTrackingTag<Material> materialTag = (SubTagTrackingTag<Material>) tag;
                    tags.add(materialTag);
                } else {
                    throw new IllegalStateException("All SubTagTrackingTags must contain Materials.");
                }
            } else {
                throw new IllegalStateException("Keyed objects must be either Materials or SubTagTrackingTags.");
            }
        }
        return new MaterialTagRecipeChoice(values, tags);
    }
}
