package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.SmokingRecipeInput;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;

import java.util.Optional;

public class SmokingRecipeProvider extends CookingRecipeProvider<SmokingRecipe, SmokingRecipeInput> {
    public static SmokingRecipeProviderFactory factory() {
        return SmokingRecipeProviderFactory.getInstance();
    }

    private SmokingRecipeProvider(SmokingRecipe recipe) {
        super(SmokingRecipeInput.class, recipe);
    }

    public static class SmokingRecipeProviderFactory extends CookingRecipeProviderFactory<net.glowstone.datapack.loader.model.external.recipe.SmokingRecipe,
                                                                                   SmokingRecipe,
                                                                                   SmokingRecipeInput,
                                                                                   SmokingRecipeProvider> {
        private static volatile SmokingRecipeProviderFactory instance = null;

        private SmokingRecipeProviderFactory() {
            super(
                net.glowstone.datapack.loader.model.external.recipe.SmokingRecipe.class,
                SmokingRecipeProvider.class,
                SmokingRecipe.class,
                SmokingRecipe::new,
                SmokingRecipeProvider::new
            );
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ SmokingRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static SmokingRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (SmokingRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new SmokingRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
