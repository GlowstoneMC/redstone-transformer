package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import net.glowstone.datapack.loader.model.external.recipe.GroupableRecipe;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.processor.generation.utils.NamespaceUtils;
import net.glowstone.datapack.recipes.RecipeProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRecipeGenerator<T1 extends Recipe, T2 extends RecipeProvider> implements RecipeGenerator<T1, T2> {
    @Override
    @SuppressWarnings("unchecked")
    public MethodSpec generateMethod(String namespaceName, String itemName, Recipe recipe) {
        return MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(getProviderClass())
            .addCode(methodBody(namespaceName, itemName, recipe))
            .build();
    }

    protected abstract CodeBlock methodBody(String namespaceName, String itemName, Recipe recipe);
}
