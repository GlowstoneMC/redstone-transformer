package net.glowstone.datapack.recipes.providers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Objects;
import java.util.Optional;

public abstract class CookingRecipeProvider<T extends CookingRecipe<T>> extends StaticRecipeProvider<FurnaceInventory, T> {
    protected CookingRecipeProvider(String namespace,
                                    String key,
                                    Material resultMaterial,
                                    int resultAmount,
                                    RecipeChoice choice,
                                    float experience,
                                    int cookingTime,
                                    CookingRecipeConstructor<T> constructor) {
        super(
            FurnaceInventory.class,
            constructor.create(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                choice,
                experience,
                cookingTime
            )
        );
    }

    public CookingRecipeProvider(String namespace,
                                 String key,
                                 Material resultMaterial,
                                 int resultAmount,
                                 Optional<String> group,
                                 RecipeChoice choice,
                                 float experience,
                                 int cookingTime,
                                 CookingRecipeConstructor<T> constructor) {
        super(
            FurnaceInventory.class,
            constructor.create(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                choice,
                experience,
                cookingTime
            )
        );
        group.ifPresent(getRecipe()::setGroup);
    }

    public CookingRecipeProvider<T> setGroup(Optional<String> group) {
        group.ifPresent(getRecipe()::setGroup);
        return this;
    }

    public CookingRecipeProvider<T> setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    @Override
    public NamespacedKey getKey() {
        return getRecipe().getKey();
    }

    @Override
    public Optional<Recipe> getRecipeFor(FurnaceInventory inventory) {
        ItemStack item = inventory.getSmelting();

        if (matchesWildcard(getRecipe().getInput(), item)) {
            return Optional.of(getRecipe());
        }

        return Optional.empty();
    }
    
    @FunctionalInterface
    public interface CookingRecipeConstructor<T extends CookingRecipe<T>> {
        T create(NamespacedKey key, ItemStack result, RecipeChoice input, float experience, int cookingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRecipe().getKey(),
            getRecipe().getResult(),
            getRecipe().getInputChoice(),
            getRecipe().getExperience(),
            getRecipe().getCookingTime(),
            getRecipe().getGroup()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CookingRecipeProvider<?> genericThat = (CookingRecipeProvider<?>) o;
        if (getRecipe().getClass() != genericThat.getRecipe().getClass()) return false;
        CookingRecipeProvider<T> that = (CookingRecipeProvider<T>) genericThat;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getInputChoice(), that.getRecipe().getInputChoice())
            && Objects.equals(getRecipe().getExperience(), that.getRecipe().getExperience())
            && Objects.equals(getRecipe().getCookingTime(), that.getRecipe().getCookingTime())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }
}
