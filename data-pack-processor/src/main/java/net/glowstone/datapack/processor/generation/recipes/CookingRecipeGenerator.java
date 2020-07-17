package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.loader.model.external.recipe.CampfireCookingRecipe;
import net.glowstone.datapack.loader.model.external.recipe.CookingRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.CampfireRecipe;

import java.util.Optional;

public class CookingRecipeGenerator<T1 extends CookingRecipe, T2 extends org.bukkit.inventory.CookingRecipe<T2>> extends AbstractCraftingRecipeGenerator<T1, T2> {
    private final Class<T1> associatedClass;
    private final Class<T2> bukkitClass;

    public CookingRecipeGenerator(Class<T1> associatedClass, Class<T2> bukkitClass) {
        this.associatedClass = associatedClass;
        this.bukkitClass = bukkitClass;
    }

    @Override
    public Class<T1> getAssociatedClass() {
        return associatedClass;
    }

    @Override
    public Class<T2> getBukkitClass() {
        return bukkitClass;
    }

    @Override
    protected Material getResultingMaterial(T1 cookingRecipe) {
        return Material.matchMaterial(cookingRecipe.getResult());
    }

    @Override
    protected int getResultCount(T1 cookingRecipe) {
        return 1;
    }

    @Override
    protected Optional<CodeBlock> extraConstructorArgs(String namespaceName, String itemName, T1 cookingRecipe) {
        return Optional.of(CodeBlock.of(
            "$L, $Lf, $L",
            Helpers.createRecipeChoice(namespaceName, cookingRecipe.getIngredient()),
            cookingRecipe.getExperience(),
            cookingRecipe.getCookingTime()
        ));
    }
}
