package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.MethodSpec;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;

public interface RecipeGenerator<T extends Recipe> {
    Class<T> getAssociatedClass();

    String getDefaultMethodName();

    @SuppressWarnings("unchecked")
    default MethodSpec generateMethod(String namespaceName,
                                      String itemName,
                                      Recipe recipe) {
        if (!getAssociatedClass().isAssignableFrom(recipe.getClass())) {
            throw new ClassCastException();
        }
        return generateMethodImpl(namespaceName, itemName, (T) recipe);
    }

    MethodSpec generateMethodImpl(String namespaceName,
                                  String itemName,
                                  T recipe);
}
