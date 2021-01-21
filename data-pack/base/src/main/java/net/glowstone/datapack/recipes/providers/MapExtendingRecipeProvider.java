package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.loader.model.external.recipe.special.MapExtendingRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.MapExtendingRecipeInput;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class MapExtendingRecipeProvider extends SpecialRecipeProvider<MapExtendingRecipeInput> {
    public static MapExtendingRecipeProviderFactory factory() {
        return MapExtendingRecipeProviderFactory.getInstance();
    }

    private static final List<Material> RECIPE = ImmutableList.<Material>builder()
        .add(Material.PAPER, Material.PAPER, Material.PAPER)
        .add(Material.PAPER, Material.FILLED_MAP, Material.PAPER)
        .add(Material.PAPER, Material.PAPER, Material.PAPER)
        .build();

    private MapExtendingRecipeProvider(String namespace, String key) {
        super(MapExtendingRecipeInput.class, new NamespacedKey(namespace, key));
    }

    @Override
    public Optional<Recipe> getRecipeFor(MapExtendingRecipeInput input) {
        if (input.getInput().length != RECIPE.size()) {
            return Optional.empty(); // Not big enough
        }

        ItemStack map = null;

        for (int i = 0; i < RECIPE.size(); i++) {
            ItemStack item = input.getInput()[i];

            if (itemStackIsEmpty(item)) {
                return Optional.empty(); // No stacks can be empty
            }

            if (item.getType() != RECIPE.get(i)) {
                return Optional.empty(); // Item doesn't match recipe.
            }

            if (item.getType() == Material.FILLED_MAP) {
                map = item;
            }
        }

        if (map == null) {
            return Optional.empty(); // Sanity check, should never happen.
        }

        //TODO: Add zooming once maps are implemented

        return Optional.of(new StaticResultRecipe(getKey(), map.clone()));
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

    public static class MapExtendingRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<MapExtendingRecipeProvider, MapExtendingRecipe> {
        private static volatile MapExtendingRecipeProviderFactory instance = null;

        private MapExtendingRecipeProviderFactory() {
            super(MapExtendingRecipe.class, MapExtendingRecipeProvider.class, MapExtendingRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ MapExtendingRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static MapExtendingRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (MapExtendingRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new MapExtendingRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
