package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.loader.model.external.recipe.CookingRecipe;
import net.glowstone.datapack.loader.model.external.recipe.StonecuttingRecipe;
import net.glowstone.datapack.recipes.StonecuttingRecipeProvider;
import org.bukkit.Material;

import java.util.Optional;

public class StonecuttingRecipeGenerator extends AbstractCraftingRecipeGenerator<StonecuttingRecipe, StonecuttingRecipeProvider> {
    @Override
    public Class<StonecuttingRecipe> getAssociatedClass() {
        return StonecuttingRecipe.class;
    }

    @Override
    public Class<StonecuttingRecipeProvider> getProviderClass() {
        return StonecuttingRecipeProvider.class;
    }

    @Override
    public String getDefaultMethodName() {
        return "defaultStonecuttingRecipes";
    }

    @Override
    protected Material getResultingMaterial(StonecuttingRecipe stonecuttingRecipe) {
        return Material.matchMaterial(stonecuttingRecipe.getResult());
    }

    @Override
    protected int getResultCount(StonecuttingRecipe stonecuttingRecipe) {
        return 1;
    }

    @Override
    protected Optional<CodeBlock> extraRecipeConstructorArgs(String namespaceName, String itemName, StonecuttingRecipe stonecuttingRecipe) {
        return Optional.of(Helpers.createRecipeChoice(namespaceName, stonecuttingRecipe.getIngredient()));
    }
}
