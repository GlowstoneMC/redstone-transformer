package net.glowstone.datapack;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.recipes.providers.mapping.RecipeProviderMapping;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Recipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractRecipeManager {
    private final Map<NamespacedKey, RecipeProvider<?>> recipesByKey;
    private final Multimap<Class<? extends Inventory>, RecipeProvider<?>> recipesByType;
    protected final AbstractTagManager tagManager;

    protected AbstractRecipeManager(AbstractTagManager tagManager) {
        this.recipesByKey = new HashMap<>();
        this.recipesByType = HashMultimap.create();
        this.tagManager = tagManager;
        loadDefaultRecipes();
    }

    public Recipe getRecipe(Inventory inventory) {
        return recipesByType.get(inventory.getClass())
            .stream()
            .map((r) -> r.getRecipeGeneric(inventory))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst()
            .orElse(null);
    }

    public void loadFromDataPack(DataPack dataPack) {
        dataPack.getNamespacedData().forEach((namespace, data) -> {
            data.getRecipes().forEach((itemName, recipe) -> {
                RecipeProviderMapping<?, ?> mapping = RecipeProviderMapping.RECIPE_PROVIDER_MAPPINGS.get(recipe.getClass());

                if (mapping != null) {
                    RecipeProvider<?> provider = mapping.providerGeneric(this.tagManager, namespace, itemName, recipe);

                    this.recipesByKey.put(provider.getKey(), provider);
                    this.recipesByType.put(provider.getInventoryClass(), provider);
                }
            });
        });
    }

    private void loadDefaultRecipes() {
        defaultRecipes()
            .forEach((provider) -> {
                this.recipesByKey.put(provider.getKey(), provider);
                this.recipesByType.put(provider.getInventoryClass(), provider);
            });
    }

    protected abstract List<RecipeProvider<?>> defaultRecipes();

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
