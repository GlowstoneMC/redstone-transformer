package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.MethodSpec;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.RecipeProvider;

public interface RecipeGenerator<T1 extends Recipe, T2 extends org.bukkit.inventory.Recipe, T3 extends RecipeProvider> {
    Class<T1> getAssociatedClass();

    Class<T2> getBukkitClass();

    Class<T3> getProviderClass();

    String getDefaultMethodName();

    MethodSpec generateMethod(String namespaceName, String itemName, Recipe recipe);
}
