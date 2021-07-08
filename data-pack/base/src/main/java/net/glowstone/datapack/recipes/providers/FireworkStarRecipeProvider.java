package net.glowstone.datapack.recipes.providers;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkStarRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.FireworkStarRecipeInput;
import net.glowstone.datapack.tags.ExtraMaterialTags;
import net.glowstone.datapack.utils.DyeUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.FireworkEffectMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class FireworkStarRecipeProvider extends SpecialRecipeProvider<FireworkStarRecipe, FireworkStarRecipeInput> {
    public static FireworkStarRecipeProviderFactory factory() {
        return FireworkStarRecipeProviderFactory.getInstance();
    }

    private static final Map<Material, FireworkEffect.Type> EFFECTS = ImmutableMap.<Material, FireworkEffect.Type>builder()
        .put(Material.FIRE_CHARGE, FireworkEffect.Type.BALL_LARGE)
        .put(Material.GOLD_NUGGET, FireworkEffect.Type.STAR)
        .put(Material.CREEPER_HEAD, FireworkEffect.Type.CREEPER)
        .put(Material.FEATHER, FireworkEffect.Type.BURST)
        .build();

    private FireworkStarRecipeProvider(String namespace, String key) {
        super(new NamespacedKey(namespace, key));
    }

    @Override
    public FireworkStarRecipeProviderFactory getFactory() {
        return factory();
    }

    @Override
    public Optional<Recipe> getRecipeFor(FireworkStarRecipeInput input) {
        boolean gunpowder = false;
        List<Color> colors = new ArrayList<>();
        FireworkEffect.Type shape = FireworkEffect.Type.BALL;
        boolean trail = false;
        boolean flicker = false;

        for (ItemStack itemStack : input.getInput()) {
            if (itemStackIsEmpty(itemStack)) {
                continue;
            }
            Material type = itemStack.getType();
            if (type == Material.GUNPOWDER) {
                if (gunpowder) {
                    return Optional.empty(); // Can't have more than one gunpowder
                }
                gunpowder = true;
                continue;
            }
            if (MaterialTags.DYES.isTagged(type)) {
                colors.add(DyeUtils.getDyeColor(type).getColor());
                continue;
            }
            if (type == Material.GLOWSTONE_DUST) {
                if (flicker) {
                    return Optional.empty(); // Can't assign flicker more than once.
                }
                flicker = true;
                continue;
            }
            if (type == Material.DIAMOND) {
                if (trail) {
                    return Optional.empty(); // Can't assign trail more than once.
                }
                trail = true;
                continue;
            }
            if (ExtraMaterialTags.HEADS.isTagged(type)) {
                type = Material.CREEPER_HEAD;
            }
            if (EFFECTS.containsKey(type)) {
                if (shape != FireworkEffect.Type.BALL) {
                    return Optional.empty(); // Can't assign more than one shape
                }
                shape = EFFECTS.get(type);
                continue;
            }

            return Optional.empty(); // Unmatched item
        }

        if (!gunpowder || colors.isEmpty()) {
            return Optional.empty(); // Missing gunpowder or dye
        }

        FireworkEffect effect = FireworkEffect.builder()
            .with(shape)
            .flicker(flicker)
            .trail(trail)
            .withColor(colors)
            .build();

        ItemStack ret = new ItemStack(Material.FIREWORK_STAR);
        FireworkEffectMeta meta = (FireworkEffectMeta) ret.getItemMeta();
        meta.setEffect(effect);

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

    public static class FireworkStarRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<FireworkStarRecipeProvider, FireworkStarRecipe, FireworkStarRecipeInput> {
        private static volatile FireworkStarRecipeProviderFactory instance = null;

        private FireworkStarRecipeProviderFactory() {
            super(FireworkStarRecipeProvider.class, FireworkStarRecipe.class, FireworkStarRecipeInput.class, FireworkStarRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ FireworkStarRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static FireworkStarRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (FireworkStarRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new FireworkStarRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
