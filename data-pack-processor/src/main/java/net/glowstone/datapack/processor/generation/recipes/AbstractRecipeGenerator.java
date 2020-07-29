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
        T1 recipeImpl = (T1) recipe;

        CodeBlock.Builder initBlock = CodeBlock.builder()
            .add(
                "return new $T($S, $S",
                getProviderClass(),
                namespaceName,
                itemName
            );

        extraConstructorArgs(namespaceName, itemName, recipeImpl)
            .ifPresent((extra) -> initBlock.add(", $L", extra));

        initBlock.add(")");

        if (recipeImpl instanceof GroupableRecipe) {
            Optional<String> group = ((GroupableRecipe)recipeImpl).getGroup();
            group.ifPresent((groupName) -> initBlock.add(".setGroup($S)", groupName));
        }

        extraBuilderCalls(namespaceName, itemName, recipeImpl).ifPresent(initBlock::add);

        return MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(getProviderClass())
            .addStatement(initBlock.build())
            .build();
    }

    protected Optional<CodeBlock> extraConstructorArgs(String namespaceName, String itemName, T1 recipe) {
        return Optional.empty();
    }

    protected Optional<CodeBlock> extraBuilderCalls(String namespaceName, String itemName, T1 recipe) {
        return Optional.empty();
    }
}
