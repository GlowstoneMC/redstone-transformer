package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.loader.model.external.recipe.special.TippedArrowRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.MapExtendingRecipeInput;
import net.glowstone.datapack.recipes.inputs.TippedArrowRecipeInput;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class TippedArrowRecipeProvider extends SpecialRecipeProvider<TippedArrowRecipe, TippedArrowRecipeInput> {
    public static TippedArrowRecipeProviderFactory factory() {
        return TippedArrowRecipeProviderFactory.getInstance();
    }

    private static final List<Material> RECIPE = ImmutableList.<Material>builder()
        .add(Material.ARROW, Material.ARROW, Material.ARROW)
        .add(Material.ARROW, Material.LINGERING_POTION, Material.ARROW)
        .add(Material.ARROW, Material.ARROW, Material.ARROW)
        .build();

    private TippedArrowRecipeProvider(String namespace, String key) {
        super(new NamespacedKey(namespace, key));
    }

    @Override
    public TippedArrowRecipeProviderFactory getFactory() {
        return factory();
    }

    @Override
    public Optional<Recipe> getRecipeFor(TippedArrowRecipeInput input) {
        if (input.getInput().length != RECIPE.size()) {
            return Optional.empty(); // Not big enough
        }

        ItemStack potion = null;

        for (int i = 0; i < RECIPE.size(); i++) {
            ItemStack item = input.getInput()[i];

            if (itemStackIsEmpty(item)) {
                return Optional.empty(); // No stacks can be empty
            }

            if (item.getType() != RECIPE.get(i)) {
                return Optional.empty(); // Item doesn't match recipe.
            }

            if (item.getType() == Material.LINGERING_POTION) {
                potion = item;
            }
        }

        if (potion == null) {
            return Optional.empty(); // Sanity check, should never happen.
        }

        ItemStack ret = new ItemStack(Material.TIPPED_ARROW);
        ret.setItemMeta(potion.getItemMeta().clone());

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

    public static class TippedArrowRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<TippedArrowRecipeProvider, TippedArrowRecipe, TippedArrowRecipeInput> {
        private static volatile TippedArrowRecipeProviderFactory instance = null;

        private TippedArrowRecipeProviderFactory() {
            super(TippedArrowRecipeProvider.class, TippedArrowRecipe.class, TippedArrowRecipeInput.class, TippedArrowRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ TippedArrowRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static TippedArrowRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (TippedArrowRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new TippedArrowRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
