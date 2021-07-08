package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.loader.model.external.recipe.SmeltingRecipe;
import net.glowstone.datapack.recipes.inputs.FurnaceRecipeInput;
import org.bukkit.inventory.FurnaceRecipe;

public class FurnaceRecipeProvider extends CookingRecipeProvider<SmeltingRecipe, FurnaceRecipeInput, FurnaceRecipe> {
    public static FurnaceRecipeProviderFactory factory() {
        return FurnaceRecipeProviderFactory.getInstance();
    }

    private FurnaceRecipeProvider(FurnaceRecipe recipe) {
        super(recipe);
    }

    @Override
    public FurnaceRecipeProviderFactory getFactory() {
        return factory();
    }

    public static class FurnaceRecipeProviderFactory extends CookingRecipeProviderFactory<FurnaceRecipeProvider,
                                                                                          SmeltingRecipe,
                                                                                          FurnaceRecipeInput,
                                                                                          FurnaceRecipe> {
        private static volatile FurnaceRecipeProviderFactory instance = null;

        private FurnaceRecipeProviderFactory() {
            super(
                FurnaceRecipeProvider.class,
                SmeltingRecipe.class,
                FurnaceRecipeInput.class,
                FurnaceRecipe.class,
                FurnaceRecipe::new,
                FurnaceRecipeProvider::new
            );
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ FurnaceRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static FurnaceRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (FurnaceRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new FurnaceRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
