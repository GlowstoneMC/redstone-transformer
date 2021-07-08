package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.RecipeInput;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.stream.Stream;

public interface RecipeProvider<RE extends Recipe, RI extends RecipeInput> {
    NamespacedKey getKey();
    Optional<org.bukkit.inventory.Recipe> getRecipeFor(RI input);
    Stream<org.bukkit.inventory.Recipe> getRecipesForResult(ItemStack result);
    Optional<org.bukkit.inventory.Recipe> getRecipeGeneric(RecipeInput input);
    RecipeProviderFactory<? extends RecipeProvider<RE, RI>, RE, RI> getFactory();

    interface RecipeProviderFactory<P extends RecipeProvider<RE, RI>, RE extends Recipe, RI extends RecipeInput> {
        Class<RE> getModelType();
        Class<RI> getInputClass();
        Class<P> getRecipeProviderType();
    }
}
