package net.glowstone.datapack.recipes.providers;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.loader.model.external.recipe.special.ArmorDyeRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.ArmorDyeRecipeInput;
import net.glowstone.datapack.tags.ExtraMaterialTags;
import net.glowstone.datapack.utils.DyeUtils;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class ArmorDyeRecipeProvider extends SpecialRecipeProvider<ArmorDyeRecipe, ArmorDyeRecipeInput> {
    public static ArmorDyeRecipeProviderFactory factory() {
        return ArmorDyeRecipeProviderFactory.getInstance();
    }

    private ArmorDyeRecipeProvider(String namespace, String key) {
        super(new NamespacedKey(namespace, key));
    }

    @Override
    public ArmorDyeRecipeProviderFactory getFactory() {
        return factory();
    }

    @Override
    public Optional<Recipe> getRecipeFor(ArmorDyeRecipeInput input) {
        ItemStack armor = null;
        List<Color> colors = new ArrayList<>();

        for (ItemStack item : input.getInput()) {
            if (itemStackIsEmpty(item)) {
                continue;
            }

            if (MaterialTags.DYES.isTagged(item.getType())) {
                Color color = DyeUtils.getDyeColor(item.getType()).getColor();
                colors.add(color);
                continue;
            }

            if (ExtraMaterialTags.DYABLE_ARMOR.isTagged(item.getType())) {
                if (armor != null) {
                    return Optional.empty(); // Can't dye more than one item
                }
                armor = item;
                continue;
            }

            return Optional.empty(); // Non-armor item
        }

        if (armor == null) {
            return Optional.empty(); // No armor
        }
        if (colors.isEmpty()) {
            return Optional.empty(); // No colors
        }

        LeatherArmorMeta meta = (LeatherArmorMeta) armor.getItemMeta();
        Color base = meta.getColor();
        if (meta.getColor() == Bukkit.getItemFactory().getDefaultLeatherColor()) {
            base = colors.remove(0);
        }

        Color newColor = base.mixColors(colors.toArray(new Color[0]));

        ItemStack ret = armor.clone();
        LeatherArmorMeta retMeta = (LeatherArmorMeta) ret.getItemMeta();
        retMeta.setColor(newColor);
        ret.setItemMeta(retMeta);

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

    public static class ArmorDyeRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<ArmorDyeRecipeProvider, ArmorDyeRecipe, ArmorDyeRecipeInput> {
        private static volatile ArmorDyeRecipeProviderFactory instance = null;

        private ArmorDyeRecipeProviderFactory() {
            super(ArmorDyeRecipeProvider.class, ArmorDyeRecipe.class, ArmorDyeRecipeInput.class, ArmorDyeRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ ArmorDyeRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static ArmorDyeRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (ArmorDyeRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new ArmorDyeRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
