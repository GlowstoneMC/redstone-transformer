package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.loader.model.external.recipe.special.BannerDuplicateRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.BannerDuplicateRecipeInput;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class BannerDuplicateRecipeProvider extends SpecialRecipeProvider<BannerDuplicateRecipe, BannerDuplicateRecipeInput> {
    public static BannerDuplicateRecipeProviderFactory factory() {
        return BannerDuplicateRecipeProviderFactory.getInstance();
    }

    private BannerDuplicateRecipeProvider(String namespace, String key) {
        super(new NamespacedKey(namespace, key));
    }

    @Override
    public BannerDuplicateRecipeProviderFactory getFactory() {
        return factory();
    }

    @Override
    public Optional<Recipe> getRecipeFor(BannerDuplicateRecipeInput input) {
        ItemStack original = null;
        ItemStack duplicate = null;

        for (ItemStack item : input.getInput()) {
            if (itemStackIsEmpty(item)) {
                continue;
            }

            if (Tag.ITEMS_BANNERS.isTagged(item.getType())) {
                if (original != null && duplicate != null) {
                    return Optional.empty(); // Can't create more than one duplicate
                }
                BannerMeta meta = (BannerMeta) item.getItemMeta();
                if (meta.getPatterns().isEmpty()) {
                    if (duplicate != null) {
                        return Optional.empty(); // More than one blank banner
                    }
                    duplicate = item;
                } else {
                    if (original != null) {
                        return Optional.empty();
                    }
                    original = item;
                }
                continue;
            }

            return Optional.empty(); // Non-matching item
        }

        if (original == null || duplicate == null) {
            return Optional.empty(); // Needs exactly two banners to duplicate
        }

        if (original.getType() != duplicate.getType()) {
            return Optional.empty(); // Both banners need to be the same color
        }

        return Optional.of(new StaticResultRecipe(getKey(), original.clone()));
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

    public static class BannerDuplicateRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<BannerDuplicateRecipeProvider, BannerDuplicateRecipe, BannerDuplicateRecipeInput> {
        private static volatile BannerDuplicateRecipeProviderFactory instance = null;

        private BannerDuplicateRecipeProviderFactory() {
            super(BannerDuplicateRecipeProvider.class, BannerDuplicateRecipe.class, BannerDuplicateRecipeInput.class, BannerDuplicateRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ BannerDuplicateRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static BannerDuplicateRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (BannerDuplicateRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new BannerDuplicateRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
