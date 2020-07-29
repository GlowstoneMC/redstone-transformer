package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.loader.model.external.recipe.CampfireCookingRecipe;
import net.glowstone.datapack.loader.model.external.recipe.CookingRecipe;
import net.glowstone.datapack.recipes.CookingRecipeProvider;
import org.bukkit.Material;
import org.bukkit.inventory.CampfireRecipe;

import java.util.Optional;

public class CookingRecipeGenerator<T1 extends CookingRecipe, T2 extends CookingRecipeProvider<T3>, T3 extends org.bukkit.inventory.CookingRecipe<T3>> extends AbstractCraftingRecipeGenerator<T1, T2> {
    private final Class<T1> associatedClass;
    private final Class<T2> providerClass;
    private final Class<T3> bukkitClass;

    public CookingRecipeGenerator(Class<T1> associatedClass, Class<T2> providerClass, Class<T3> bukkitClass) {
        this.associatedClass = associatedClass;
        this.providerClass = providerClass;
        this.bukkitClass = bukkitClass;
    }

    @Override
    public Class<T1> getAssociatedClass() {
        return this.associatedClass;
    }

    @Override
    public Class<T2> getProviderClass() {
        return this.providerClass;
    }

    @Override
    public String getDefaultMethodName() {
        return "default" + bukkitClass.getSimpleName() + "s";
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
    protected Optional<CodeBlock> extraRecipeConstructorArgs(String namespaceName, String itemName, T1 cookingRecipe) {
        return Optional.of(CodeBlock.of(
            "$L, $Lf, $L, $T::new",
            Helpers.createRecipeChoice(namespaceName, cookingRecipe.getIngredient()),
            cookingRecipe.getExperience(),
            cookingRecipe.getCookingTime(),
            bukkitClass
        ));
    }
}
