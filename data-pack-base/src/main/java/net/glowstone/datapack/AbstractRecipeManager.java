package net.glowstone.datapack;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.recipes.providers.mapping.RecipeProviderMappingRegistry;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.apache.commons.lang3.ClassUtils;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
    private final Map<NamespacedKey, RecipeProvider<?>> recipesByKey;
    private final Multimap<Class<? extends Inventory>, RecipeProvider<?>> recipesByType;
    private final LoadingCache<Class<? extends Inventory>, Set<Class<? extends Inventory>>> inventoryTypeCache;
    protected final TagManager tagManager;

    protected AbstractRecipeManager(TagManager tagManager) {
        this.recipesByKey = new HashMap<>();
        this.recipesByType = HashMultimap.create();
        this.inventoryTypeCache = CacheBuilder.newBuilder()
            .build(
                new CacheLoader<Class<? extends Inventory>, Set<Class<? extends Inventory>>>() {
                    @Override
                    public Set<Class<? extends Inventory>> load(Class<? extends Inventory> key) {
                        Set<Class<?>> possibleClasses = new HashSet<>();
                        possibleClasses.add(key);
                        List<Class<?>> superClasses = ClassUtils.getAllSuperclasses(key);
                        possibleClasses.addAll(superClasses);
                        superClasses.forEach((superClass) -> possibleClasses.addAll(ClassUtils.getAllInterfaces(superClass)));
                        return possibleClasses.stream()
                            .filter(Inventory.class::isAssignableFrom)
                            .map((clazz) -> (Class<? extends Inventory>) clazz)
                            .collect(Collectors.toSet());
                    }
                }
            );
        this.tagManager = tagManager;
        loadDefaultRecipes();
    }

    @Override
    public Set<NamespacedKey> getAllRecipeKeys() {
        return ImmutableSet.copyOf(recipesByKey.keySet());
    }

    @Override
    public Optional<RecipeProvider<?>> getRecipeProvider(NamespacedKey key) {
        return Optional.ofNullable(recipesByKey.get(key));
    }

    @Override
    public Recipe getRecipe(Inventory inventory) {
        Set<Class<? extends Inventory>> allClasses = inventoryTypeCache.getUnchecked(inventory.getClass());

        return allClasses
            .stream()
            .flatMap((clazz) -> recipesByType.get((Class<? extends Inventory>) clazz).stream())
            .distinct()
            .map((r) -> r.getRecipeGeneric(inventory))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst()
            .orElse(null);
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
                Optional<RecipeProvider<?>> possibleProvider = RecipeProviderMappingRegistry.provider(this.tagManager, namespace, itemName, recipe);

                if (possibleProvider.isPresent()) {
                    RecipeProvider<?> provider = possibleProvider.get();

                    this.recipesByKey.put(provider.getKey(), provider);
                    this.recipesByType.put(provider.getInventoryClass(), provider);
                }
            });
        });
    }

    @Override
    public void resetToDefaults() {
        recipesByKey.clear();
        recipesByType.clear();
        loadDefaultRecipes();
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
