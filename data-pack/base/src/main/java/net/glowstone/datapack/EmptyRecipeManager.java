package net.glowstone.datapack;

import net.glowstone.datapack.recipes.providers.RecipeProvider;

import java.util.Collections;
import java.util.List;

public class EmptyRecipeManager extends AbstractRecipeManager {
    public EmptyRecipeManager(TagManager tagManager) {
        super(tagManager);
    }

    @Override
    protected List<RecipeProvider<?, ?>> defaultRecipes() {
        return Collections.emptyList();
    }
}
