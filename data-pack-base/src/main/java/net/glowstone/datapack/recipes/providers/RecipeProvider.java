package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.RecipeInput;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Optional;
import java.util.stream.Stream;

public interface RecipeProvider<I extends RecipeInput> {
    NamespacedKey getKey();
    Class<I> getInputClass();
    Optional<Recipe> getRecipeFor(I input);
    Stream<Recipe> getRecipesForResult(ItemStack result);
    @SuppressWarnings("unchecked")
    default Optional<Recipe> getRecipeGeneric(RecipeInput input) {
        return this.getRecipeFor((I) input);
    }
}
