package net.glowstone.datapack.processor.generation.recipes;

import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.RecipeProvider;

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
}
