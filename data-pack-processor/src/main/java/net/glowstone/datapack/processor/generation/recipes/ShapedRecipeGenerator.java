package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.loader.model.external.recipe.ShapedRecipe;
import net.glowstone.datapack.recipes.ShapedRecipeProvider;
import org.bukkit.Material;

import java.util.Optional;

public class ShapedRecipeGenerator extends AbstractCraftingRecipeGenerator<ShapedRecipe, ShapedRecipeProvider, org.bukkit.inventory.ShapedRecipe> {
    @Override
    public Class<ShapedRecipe> getAssociatedClass() {
        return ShapedRecipe.class;
    }

    @Override
    public Class<org.bukkit.inventory.ShapedRecipe> getBukkitClass() {
        return org.bukkit.inventory.ShapedRecipe.class;
    }

    @Override
    public Class<ShapedRecipeProvider> getProviderClass() {
        return ShapedRecipeProvider.class;
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
    protected Optional<CodeBlock> extraRecipeCode(String namespaceName, String itemName, ShapedRecipe shapedRecipe, Material resultingMaterial) {
        CodeBlock.Builder methodBlock = CodeBlock.builder()
            .addStatement(
                CodeBlock.builder()
                    .add(
                        "recipe.shape(new $T[] {",
                        String.class
                    )
                    .add(
                        shapedRecipe.getPattern()
                            .stream()
                            .map((pattern) -> CodeBlock.of("$S", pattern))
                            .collect(CodeBlock.joining(", "))
                    )
                    .add("})")
                    .build()
            );

        shapedRecipe.getKey().forEach((key, ingredients) -> {
            methodBlock.addStatement(
                "recipe.setIngredient('$L', $L)",
                key,
                Helpers.createRecipeChoice(namespaceName, ingredients)
            );
        });

        return Optional.of(methodBlock.build());
    }
}
