package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.loader.model.external.recipe.special.FireworkRocketRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.FireworkRocketRecipeInput;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class FireworkRocketRecipeProvider extends SpecialRecipeProvider<FireworkRocketRecipe, FireworkRocketRecipeInput> {
    public static FireworkRocketRecipeProviderFactory factory() {
        return FireworkRocketRecipeProviderFactory.getInstance();
    }

    private FireworkRocketRecipeProvider(String namespace, String key) {
        super(new NamespacedKey(namespace, key));
    }

    @Override
    public FireworkRocketRecipeProviderFactory getFactory() {
        return factory();
    }

    @Override
    public Optional<Recipe> getRecipeFor(FireworkRocketRecipeInput input) {
        List<FireworkEffect> effects = new ArrayList<>();
        boolean paper = false;
        int duration = 0;

        for (ItemStack itemStack : input.getInput()) {
            if (itemStackIsEmpty(itemStack)) {
                continue;
            }
            if (itemStack.getType() == Material.PAPER) {
                if (paper) {
                    return Optional.empty(); // Can't have more than one paper
                }
                paper = true;
            }
            if (itemStack.getType() == Material.GUNPOWDER) {
                duration += 1;
                continue;
            }
            if (itemStack.getType() == Material.FIREWORK_STAR) {
                effects.add(((FireworkEffectMeta) itemStack.getItemMeta()).getEffect());
                continue;
            }

            return Optional.empty(); // Unmatched item
        }

        if (!paper || duration == 0 || duration > 3) {
            return Optional.empty(); // Missing paper or gunpowder, or too much gunpowder
        }

        ItemStack ret = new ItemStack(Material.FIREWORK_ROCKET, 3);
        FireworkMeta meta = (FireworkMeta) ret.getItemMeta();
        meta.setPower(duration);
        meta.addEffects(effects);

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

    public static class FireworkRocketRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<FireworkRocketRecipeProvider, FireworkRocketRecipe, FireworkRocketRecipeInput> {
        private static volatile FireworkRocketRecipeProviderFactory instance = null;

        private FireworkRocketRecipeProviderFactory() {
            super(FireworkRocketRecipeProvider.class, FireworkRocketRecipe.class, FireworkRocketRecipeInput.class, FireworkRocketRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ FireworkRocketRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static FireworkRocketRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (FireworkRocketRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new FireworkRocketRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
