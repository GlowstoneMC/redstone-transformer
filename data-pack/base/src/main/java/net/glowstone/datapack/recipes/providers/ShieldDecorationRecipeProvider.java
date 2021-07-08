package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.loader.model.external.recipe.special.ShieldDecorationRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.ShieldDecorationRecipeInput;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class ShieldDecorationRecipeProvider extends SpecialRecipeProvider<ShieldDecorationRecipe, ShieldDecorationRecipeInput> {
    public static ShieldDecorationRecipeProviderFactory factory() {
        return ShieldDecorationRecipeProviderFactory.getInstance();
    }

    private ShieldDecorationRecipeProvider(String namespace, String key) {
        super(new NamespacedKey(namespace, key));
    }

    @Override
    public ShieldDecorationRecipeProviderFactory getFactory() {
        return factory();
    }

    @Override
    public Optional<Recipe> getRecipeFor(ShieldDecorationRecipeInput input) {
        ItemStack shield = null;
        ItemStack banner = null;

        for (ItemStack item : input.getInput()) {
            if (itemStackIsEmpty(item)) {
                continue;
            }

            if (Tag.ITEMS_BANNERS.isTagged(item.getType())) {
                if (banner != null) {
                    return Optional.empty(); // Can't combine banners
                }
                banner = item;
                continue;
            }

            if (Material.SHIELD.equals(item.getType())) {
                if (shield != null) {
                    return Optional.empty(); // Can't decorate more than one shield
                }
                shield = item;
                continue;
            }

            return Optional.empty(); // Non-matching item
        }

        if (shield == null || banner == null) {
            return Optional.empty(); // No shield or banner
        }

        BannerMeta shieldMeta = (BannerMeta) shield.getItemMeta();
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        if (!shieldMeta.getPatterns().isEmpty()) {
            return Optional.empty(); // Can't redecorate a shield.
        }

        ItemStack ret = shield.clone();
        ret.setItemMeta(bannerMeta);

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

    public static class ShieldDecorationRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<ShieldDecorationRecipeProvider, ShieldDecorationRecipe, ShieldDecorationRecipeInput> {
        private static volatile ShieldDecorationRecipeProviderFactory instance = null;

        private ShieldDecorationRecipeProviderFactory() {
            super(ShieldDecorationRecipeProvider.class, ShieldDecorationRecipe.class, ShieldDecorationRecipeInput.class, ShieldDecorationRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ ShieldDecorationRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static ShieldDecorationRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (ShieldDecorationRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new ShieldDecorationRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
