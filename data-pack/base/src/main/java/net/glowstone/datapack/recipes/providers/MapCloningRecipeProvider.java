package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.loader.model.external.recipe.special.MapCloningRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.MapCloningRecipeInput;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class MapCloningRecipeProvider extends SpecialRecipeProvider<MapCloningRecipe, MapCloningRecipeInput> {
    public static MapCloningRecipeProviderFactory factory() {
        return MapCloningRecipeProviderFactory.getInstance();
    }

    private MapCloningRecipeProvider(String namespace, String key) {
        super(new NamespacedKey(namespace, key));
    }

    @Override
    public MapCloningRecipeProviderFactory getFactory() {
        return factory();
    }

    @Override
    public Optional<Recipe> getRecipeFor(MapCloningRecipeInput input) {
        ItemStack original = null;
        int copies = 0;

        for (ItemStack itemStack : input.getInput()) {
            if (itemStackIsEmpty(itemStack)) {
                continue;
            }
            if (itemStack.getType() == Material.FILLED_MAP) {
                if (original == null) {
                    original = itemStack;
                    continue;
                }
                return Optional.empty(); // Can't have more than one cloneable map
            }
            if (itemStack.getType() == Material.MAP) {
                copies += 1;
                continue;
            }

            return Optional.empty(); // Unmatched item
        }

        if (original == null || copies == 0) {
            return Optional.empty(); // Either a filled map or an empty map was missing
        }

        ItemStack ret = original.clone();
        ret.setAmount(copies);

        return Optional.of(new StaticResultRecipe(getKey(), ret));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    public static class MapCloningRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<MapCloningRecipeProvider, MapCloningRecipe, MapCloningRecipeInput> {
        private static volatile MapCloningRecipeProviderFactory instance = null;

        private MapCloningRecipeProviderFactory() {
            super(MapCloningRecipeProvider.class, MapCloningRecipe.class, MapCloningRecipeInput.class, MapCloningRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ MapCloningRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static MapCloningRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (MapCloningRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new MapCloningRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
