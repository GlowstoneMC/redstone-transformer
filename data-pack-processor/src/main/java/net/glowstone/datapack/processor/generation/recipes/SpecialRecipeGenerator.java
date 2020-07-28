package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.RecipeProvider;

import java.util.Optional;

public class SpecialRecipeGenerator<T1 extends Recipe, T2 extends RecipeProvider> extends AbstractRecipeGenerator<T1, T2> {
    private final Class<T1> associatedClass;
    private final Class<T2> providerClass;

    public SpecialRecipeGenerator(Class<T1> associatedClass, Class<T2> providerClass) {
        this.associatedClass = associatedClass;
        this.providerClass = providerClass;
    }

    @Override
    public Class<T1> getAssociatedClass() {
        return associatedClass;
    }

    @Override
    public Class<T2> getProviderClass() {
        return providerClass;
    }

    @Override
    public String getDefaultMethodName() {
        return "defaultSpecialRecipes";
    }

    @Override
    protected CodeBlock methodBody(String namespaceName, String itemName, Recipe recipe) {
        @SuppressWarnings("unchecked")
        T1 recipeImpl = (T1) recipe;

        CodeBlock.Builder constructor = CodeBlock.builder()
            .add(
                "new $T($S, $S",
                getProviderClass(),
                namespaceName,
                itemName
            );

        extraConstructorArgs(namespaceName, itemName, recipeImpl)
            .ifPresent((extra) -> constructor.add(", $L", extra));

        constructor.add(")");

        return CodeBlock.builder()
            .addStatement(
                "return $L",
                constructor.build()
            )
            .build();
    }

    protected Optional<CodeBlock> extraConstructorArgs(String namespaceName, String itemName, T1 recipe) {
        return Optional.empty();
    }
}
