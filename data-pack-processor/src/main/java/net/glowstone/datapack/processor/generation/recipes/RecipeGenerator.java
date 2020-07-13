package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.MethodSpec;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;

import java.util.Map;
import java.util.Set;

public interface RecipeGenerator<T extends Recipe> {
    Class<T> getAssociatedClass();

    String getDefaultMethodName();

    @SuppressWarnings("unchecked")
    default MethodSpec generateMethod(String namespaceName,
                                      String itemName,
                                      Map<String, Map<String, Set<String>>> namespacedTaggedItems,
                                      Recipe recipe) {
        if (!getAssociatedClass().isAssignableFrom(recipe.getClass())) {
            throw new ClassCastException();
        }
        return generateMethodImpl(namespaceName, itemName, namespacedTaggedItems, (T) recipe);
    }

    MethodSpec generateMethodImpl(String namespaceName,
                              String itemName,
                              Map<String, Map<String, Set<String>>> namespacedTaggedItems,
                              T recipe);
}
