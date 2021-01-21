package net.glowstone.datapack.recipes.providers;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkStarFadeRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkStarRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.FireworkStarRecipeInput;
import net.glowstone.datapack.utils.DyeUtils;
import net.glowstone.datapack.utils.mapping.MappingArgument;
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

public class FireworkStarFadeRecipeProvider extends SpecialRecipeProvider<FireworkStarRecipeInput> {
    public static FireworkStarFadeRecipeProviderFactory factory() {
        return FireworkStarFadeRecipeProviderFactory.getInstance();
    }

    private FireworkStarFadeRecipeProvider(String namespace, String key) {
        super(FireworkStarRecipeInput.class, new NamespacedKey(namespace, key));
    }

    @Override
    public Optional<Recipe> getRecipeFor(FireworkStarRecipeInput input) {
        ItemStack star = null;
        List<Color> colors = new ArrayList<>();

        for (ItemStack itemStack : input.getInput()) {
            if (itemStackIsEmpty(itemStack)) {
                continue;
            }
            Material type = itemStack.getType();
            if (type == Material.FIREWORK_STAR) {
                if (star != null) {
                    return Optional.empty(); // Can't have more than one star
                }
                star = itemStack;
                continue;
            }
            if (MaterialTags.DYES.isTagged(type)) {
                colors.add(DyeUtils.getDyeColor(type).getColor());
                continue;
            }

            return Optional.empty(); // Unmatched item
        }

        if (star == null || colors.isEmpty()) {
            return Optional.empty(); // Missing gunpowder or dye
        }

        ItemStack ret = star.clone();
        FireworkEffectMeta meta = (FireworkEffectMeta) ret.getItemMeta();
        FireworkEffect oldEffect = meta.getEffect();
        if (oldEffect != null) {
            FireworkEffect newEffect = FireworkEffect.builder()
                .with(oldEffect.getType())
                .flicker(oldEffect.hasFlicker())
                .trail(oldEffect.hasTrail())
                .withColor(oldEffect.getColors())
                .withFade(colors)
                .build();
            meta.setEffect(newEffect);
        }

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

    public static class FireworkStarFadeRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<FireworkStarFadeRecipeProvider, FireworkStarFadeRecipe> {
        private static volatile FireworkStarFadeRecipeProviderFactory instance = null;

        private FireworkStarFadeRecipeProviderFactory() {
            super(FireworkStarFadeRecipe.class, FireworkStarFadeRecipeProvider.class, FireworkStarFadeRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ FireworkStarFadeRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static FireworkStarFadeRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (FireworkStarFadeRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new FireworkStarFadeRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
