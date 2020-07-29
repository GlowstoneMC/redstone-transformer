package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.loader.model.external.recipe.ShapedRecipe;
import net.glowstone.datapack.recipes.ShapedRecipeProvider;
import org.bukkit.Material;

import java.util.Optional;

public class ShapedRecipeGenerator extends AbstractCraftingRecipeGenerator<ShapedRecipe, ShapedRecipeProvider> {
    @Override
    public Class<ShapedRecipe> getAssociatedClass() {
        return ShapedRecipe.class;
    }

    @Override
    public Class<ShapedRecipeProvider> getProviderClass() {
        return ShapedRecipeProvider.class;
    }

    @Override
    public String getDefaultMethodName() {
        return "defaultShapedRecipes";
    }

    @Override
    protected Material getResultingMaterial(ShapedRecipe shapedRecipe) {
        return Material.matchMaterial(shapedRecipe.getResult().getItem());
    }

    @Override
    protected int getResultCount(ShapedRecipe shapedRecipe) {
        return shapedRecipe.getResult().getCount();
    }

    @Override
    protected Optional<CodeBlock> extraBuilderCalls(String namespaceName, String itemName, ShapedRecipe shapedRecipe) {
        CodeBlock.Builder methodBlock = CodeBlock.builder()
            .add(
                ".setShape(new $T[] {",
                String.class
            )
            .add(
                shapedRecipe.getPattern()
                    .stream()
                    .map((pattern) -> CodeBlock.of("$S", pattern))
                    .collect(CodeBlock.joining(", "))
            )
            .add("})");

        shapedRecipe.getKey().forEach((key, ingredients) -> {
            methodBlock.add(
                ".setIngredient('$L', $L)",
                key,
                Helpers.createRecipeChoice(namespaceName, ingredients)
            );
        });

        return Optional.of(methodBlock.build());
    }
}
