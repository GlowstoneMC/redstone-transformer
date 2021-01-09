package net.glowstone.datapack.recipes.providers.mapping;

import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.recipes.providers.ShapedRecipeProvider;
import org.bukkit.Keyed;

import java.util.Optional;

public interface StaticRecipeProviderMapping<P extends RecipeProvider<?>, RE extends Recipe, RB extends org.bukkit.inventory.Recipe> extends RecipeProviderMapping<P, RE> {
    Class<RB> getBukkitType();
    P provider(RB recipe);
}
