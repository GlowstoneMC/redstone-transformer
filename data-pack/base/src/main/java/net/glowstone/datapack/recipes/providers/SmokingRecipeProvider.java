package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.loader.model.external.recipe.SmokingRecipe;
import net.glowstone.datapack.recipes.inputs.SmokingRecipeInput;

public class SmokingRecipeProvider extends CookingRecipeProvider<SmokingRecipe, SmokingRecipeInput, org.bukkit.inventory.SmokingRecipe> {
    public static SmokingRecipeProviderFactory factory() {
        return SmokingRecipeProviderFactory.getInstance();
    }

    private SmokingRecipeProvider(org.bukkit.inventory.SmokingRecipe recipe) {
        super(recipe);
    }

    @Override
    public SmokingRecipeProviderFactory getFactory() {
        return factory();
    }

    public static class SmokingRecipeProviderFactory extends CookingRecipeProviderFactory<SmokingRecipeProvider,
                                                                                          SmokingRecipe,
                                                                                          SmokingRecipeInput,
                                                                                          org.bukkit.inventory.SmokingRecipe> {
        private static volatile SmokingRecipeProviderFactory instance = null;

        private SmokingRecipeProviderFactory() {
            super(
                SmokingRecipeProvider.class,
                SmokingRecipe.class,
                SmokingRecipeInput.class,
                org.bukkit.inventory.SmokingRecipe.class,
                org.bukkit.inventory.SmokingRecipe::new,
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
