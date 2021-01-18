package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.SmithingRecipeInput;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;

import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class SmithingRecipeProvider extends StaticRecipeProvider<SmithingRecipe, SmithingRecipeInput> {
    public SmithingRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount, RecipeChoice equipment, RecipeChoice mineral) {
        super(
            SmithingRecipeInput.class,
            new SmithingRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                equipment,
                mineral
            )
        );
    }

    public SmithingRecipeProvider(SmithingRecipe recipe) {
        super(SmithingRecipeInput.class, recipe);
    }

    @Override
    public Optional<Recipe> getRecipeFor(SmithingRecipeInput input) {
        ItemStack equipment = input.getInputEquipment();
        ItemStack mineral = input.getInputEquipment();

        if (itemStackIsEmpty(equipment) || itemStackIsEmpty(mineral)) {
            return Optional.empty();
        }

        if (getRecipe().getBase().test(equipment) && getRecipe().getAddition().test(mineral)) {
            return Optional.of(getRecipe());
        }

        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRecipe().getKey(),
            getRecipe().getResult(),
            getRecipe().getBase(),
            getRecipe().getAddition()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmithingRecipeProvider that = (SmithingRecipeProvider) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getBase(), that.getRecipe().getBase())
            && Objects.equals(getRecipe().getAddition(), that.getRecipe().getAddition());
    }
}
