package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.loader.model.external.recipe.BlastingRecipe;
import net.glowstone.datapack.recipes.inputs.BlastingRecipeInput;

public class BlastingRecipeProvider extends CookingRecipeProvider<BlastingRecipe, BlastingRecipeInput, org.bukkit.inventory.BlastingRecipe> {
    public static BlastingRecipeProviderFactory factory() {
        return BlastingRecipeProviderFactory.getInstance();
    }

    private BlastingRecipeProvider(org.bukkit.inventory.BlastingRecipe recipe) {
        super(recipe);
    }

    @Override
    public BlastingRecipeProviderFactory getFactory() {
        return factory();
    }

    public static class BlastingRecipeProviderFactory extends CookingRecipeProviderFactory<BlastingRecipeProvider,
                                                                                           BlastingRecipe,
                                                                                           BlastingRecipeInput,
                                                                                           org.bukkit.inventory.BlastingRecipe> {
        private static volatile BlastingRecipeProviderFactory instance = null;

        private BlastingRecipeProviderFactory() {
            super(
                BlastingRecipeProvider.class,
                BlastingRecipe.class,
                BlastingRecipeInput.class,
                org.bukkit.inventory.BlastingRecipe.class,
                org.bukkit.inventory.BlastingRecipe::new,
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
