package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.CookingRecipeInput;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public abstract class CookingRecipeProvider<T extends CookingRecipe<T>, I extends CookingRecipeInput> extends StaticRecipeProvider<T, I> {
    protected CookingRecipeProvider(Class<I> inputClass,
                                    String namespace,
                                    String key,
                                    Material resultMaterial,
                                    int resultAmount,
                                    RecipeChoice choice,
                                    float experience,
                                    int cookingTime,
                                    CookingRecipeConstructor<T> constructor) {
        this(
            inputClass,
            constructor.create(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                choice,
                experience,
                cookingTime
            )
        );
    }

    public CookingRecipeProvider(Class<I> inputClass,
                                 String namespace,
                                 String key,
                                 Material resultMaterial,
                                 int resultAmount,
                                 Optional<String> group,
                                 RecipeChoice choice,
                                 float experience,
                                 int cookingTime,
                                 CookingRecipeConstructor<T> constructor) {
        this(
            inputClass,
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

    public CookingRecipeProvider(Class<I> inputClass, T recipe) {
        super(
            inputClass,
            recipe
        );
    }

    public CookingRecipeProvider<T, I> setGroup(Optional<String> group) {
        group.ifPresent(getRecipe()::setGroup);
        return this;
    }

    public CookingRecipeProvider<T, I> setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    @Override
    public NamespacedKey getKey() {
        return getRecipe().getKey();
    }

    @Override
    public Optional<Recipe> getRecipeFor(I input) {
        if (matchesWildcard(getRecipe().getInput(), input.getInput())) {
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
        CookingRecipeProvider<?, ?> genericThat = (CookingRecipeProvider<?, ?>) o;
        if (getRecipe().getClass() != genericThat.getRecipe().getClass()) return false;
        if (getInputClass() != genericThat.getInputClass()) return false;
        @SuppressWarnings("unchecked")
        CookingRecipeProvider<T, I> that = (CookingRecipeProvider<T, I>) genericThat;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getInputChoice(), that.getRecipe().getInputChoice())
            && Objects.equals(getRecipe().getExperience(), that.getRecipe().getExperience())
            && Objects.equals(getRecipe().getCookingTime(), that.getRecipe().getCookingTime())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }
}
