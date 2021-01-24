package net.glowstone.datapack;

import com.google.common.collect.ImmutableSet;
import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.recipes.providers.StaticRecipeProvider;
import net.glowstone.datapack.recipes.inputs.RecipeInput;
import net.glowstone.datapack.recipes.inputs.RecipeInputRegistry;
import net.glowstone.datapack.recipes.providers.RecipeProviderRegistry;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractRecipeManager implements RecipeManager {
    private final Map<NamespacedKey, RecipeProvider<?, ?>> recipesByKey;
    private final Map<Class<? extends RecipeInput>, RecipeProvider<?, ?>> recipesByInputType;
    protected final TagManager tagManager;

    protected AbstractRecipeManager(TagManager tagManager) {
        this.recipesByKey = new HashMap<>();
        this.recipesByInputType = new HashMap<>();
        this.tagManager = tagManager;
        loadDefaultRecipes();
    }

    @Override
    public Set<NamespacedKey> getAllRecipeKeys() {
        return ImmutableSet.copyOf(recipesByKey.keySet());
    }

    @Override
    public Optional<RecipeProvider<?, ?>> getRecipeProvider(NamespacedKey key) {
        return Optional.ofNullable(recipesByKey.get(key));
    }

    @Override
    public Recipe getRecipe(Inventory inventory) {
        return RecipeInputRegistry
            .from(inventory)
            .stream()
            .map((input) -> recipesByInputType.get(input.getClass()).getRecipeGeneric(input))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Recipe getRecipe(InventoryType inventoryType, ItemStack[] itemStacks) {
        return RecipeInputRegistry
            .from(inventoryType, itemStacks)
            .stream()
            .map((input) -> recipesByInputType.get(input.getClass()).getRecipeGeneric(input))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Recipe getRecipe(RecipeInput input) {
        if (!recipesByInputType.containsKey(input.getClass())) {
            return null;
        }

        return recipesByInputType.get(input.getClass())
            .getRecipeGeneric(input)
            .orElse(null);
    }

    @Override
    public Recipe getRecipe(NamespacedKey key) {
        if (recipesByKey.containsKey(key)) {
            RecipeProvider<?, ?> recipeProvider = recipesByKey.get(key);
            if (recipeProvider instanceof StaticRecipeProvider) {
                StaticRecipeProvider<?, ?, ?> staticRecipeProvider = (StaticRecipeProvider<?, ?, ?>) recipeProvider;
                return staticRecipeProvider.getRecipe();
            }
        }

        return null;
    }

    @Override
    public List<Recipe> getAllRecipesForResult(ItemStack result) {
        // handling for old-style wildcards
        ItemStack adjustedResult;
        if (result.getDurability() == -1) {
            adjustedResult = result.clone();
            adjustedResult.setDurability(Short.MAX_VALUE);
        } else {
            adjustedResult = result;
        }

        return recipesByKey.values()
            .stream()
            .flatMap((provider) -> provider.getRecipesForResult(adjustedResult))
            .collect(Collectors.toList());
    }

    @Override
    public Iterator<Recipe> getAllRecipes() {
        return null;
    }

    @Override
    public void loadFromDataPack(DataPack dataPack) {
        dataPack.getNamespacedData().forEach((namespace, data) -> {
            data.getRecipes().forEach((itemName, recipe) -> {
                Optional<RecipeProvider<?, ?>> possibleProvider = RecipeProviderRegistry.provider(this.tagManager, namespace, itemName, recipe);

                if (possibleProvider.isPresent()) {
                    RecipeProvider<?, ?> provider = possibleProvider.get();

                    this.recipesByKey.put(provider.getKey(), provider);
                    this.recipesByInputType.put(provider.getFactory().getInputClass(), provider);
                }
            });
        });
    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        Optional<StaticRecipeProvider<?, ?, ?>> possibleProvider = RecipeProviderRegistry.provider(recipe);

        if (!possibleProvider.isPresent()) {
            return false;
        }

        StaticRecipeProvider<?, ?, ?> provider = possibleProvider.get();

        this.recipesByKey.put(provider.getKey(), provider);
        this.recipesByInputType.put(provider.getFactory().getInputClass(), provider);

        return true;
    }

    @Override
    public boolean removeRecipe(NamespacedKey key) {
        RecipeProvider<?, ?> provider = this.recipesByKey.remove(key);

        if (provider != null) {
            this.recipesByInputType.remove(provider.getFactory().getInputClass(), provider);
        }

        return provider != null;
    }

    @Override
    public void resetToDefaults() {
        clearRecipes();
        loadDefaultRecipes();
    }

    @Override
    public void clearRecipes() {
        recipesByKey.clear();
        recipesByInputType.clear();
    }

    private void loadDefaultRecipes() {
        defaultRecipes()
            .forEach((provider) -> {
                this.recipesByKey.put(provider.getKey(), provider);
                this.recipesByInputType.put(provider.getFactory().getInputClass(), provider);
            });
    }

    protected abstract List<RecipeProvider<?, ?>> defaultRecipes();

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
