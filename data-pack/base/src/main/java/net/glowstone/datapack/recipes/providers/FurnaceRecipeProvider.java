package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.loader.model.external.recipe.SmeltingRecipe;
import net.glowstone.datapack.recipes.inputs.FurnaceRecipeInput;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Optional;

public class FurnaceRecipeProvider extends CookingRecipeProvider<FurnaceRecipe, FurnaceRecipeInput> {
    public static FurnaceRecipeProviderFactory factory() {
        return FurnaceRecipeProviderFactory.getInstance();
    }

    private FurnaceRecipeProvider(FurnaceRecipe recipe) {
        super(FurnaceRecipeInput.class, recipe);
    }

    public static class FurnaceRecipeProviderFactory extends CookingRecipeProviderFactory<SmeltingRecipe,
                                                                                   FurnaceRecipe,
                                                                                   FurnaceRecipeInput,
                                                                                   FurnaceRecipeProvider> {
        private static volatile FurnaceRecipeProviderFactory instance = null;

        private FurnaceRecipeProviderFactory() {
            super(
                SmeltingRecipe.class,
                FurnaceRecipeProvider.class,
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
