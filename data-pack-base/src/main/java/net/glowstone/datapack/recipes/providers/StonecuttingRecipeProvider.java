package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.recipes.inputs.StonecuttingRecipeInput;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.StonecuttingRecipe;

import java.util.Objects;
import java.util.Optional;

public class StonecuttingRecipeProvider extends StaticRecipeProvider<StonecuttingRecipe, StonecuttingRecipeInput> {
    public StonecuttingRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount, Optional<String> group, RecipeChoice source) {
        this(
            new StonecuttingRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                source
            )
        );
        group.ifPresent(getRecipe()::setGroup);
    }

    public StonecuttingRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount, MaterialTagRecipeChoice source) {
        this(
            new StonecuttingRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                source
            )
        );
    }

    public StonecuttingRecipeProvider(StonecuttingRecipe recipe) {
        super(
            StonecuttingRecipeInput.class,
            recipe
        );
    }

    public StonecuttingRecipeProvider setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    @Override
    public Optional<Recipe> getRecipeFor(StonecuttingRecipeInput input) {
        if (matchesWildcard(getRecipe().getInput(), input.getInput())) {
            return Optional.of(getRecipe());
        }
        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRecipe().getKey(),
            getRecipe().getResult(),
            getRecipe().getInputChoice(),
            getRecipe().getGroup()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StonecuttingRecipeProvider that = (StonecuttingRecipeProvider) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getInputChoice(), that.getRecipe().getInputChoice())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }
}
