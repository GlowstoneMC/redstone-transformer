package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.inputs.BlastingRecipeInput;
import org.bukkit.Material;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Optional;

public class BlastingRecipeProvider extends CookingRecipeProvider<BlastingRecipe, BlastingRecipeInput> {
    public static BlastingRecipeProviderFactory factory() {
        return BlastingRecipeProviderFactory.getInstance();
    }

    private BlastingRecipeProvider(BlastingRecipe recipe) {
        super(BlastingRecipeInput.class, recipe);
    }

    public static class BlastingRecipeProviderFactory extends CookingRecipeProviderFactory<net.glowstone.datapack.loader.model.external.recipe.BlastingRecipe,
                                                                                    BlastingRecipe,
                                                                                    BlastingRecipeInput,
                                                                                    BlastingRecipeProvider> {
        private static volatile BlastingRecipeProviderFactory instance = null;

        private BlastingRecipeProviderFactory() {
            super(
                net.glowstone.datapack.loader.model.external.recipe.BlastingRecipe.class,
                BlastingRecipeProvider.class,
                BlastingRecipe.class,
                BlastingRecipe::new,
                BlastingRecipeProvider::new
            );
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ BlastingRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static BlastingRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (BlastingRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new BlastingRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
