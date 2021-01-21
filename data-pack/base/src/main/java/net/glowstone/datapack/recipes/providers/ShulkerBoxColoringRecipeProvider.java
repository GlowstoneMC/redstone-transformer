package net.glowstone.datapack.recipes.providers;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.loader.model.external.recipe.special.ShulkerBoxColoringRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.ShulkerBoxColoringRecipeInput;
import net.glowstone.datapack.utils.DyeUtils;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class ShulkerBoxColoringRecipeProvider extends SpecialRecipeProvider<ShulkerBoxColoringRecipeInput> {
    public static ShulkerBoxColoringRecipeProviderFactory factory() {
        return ShulkerBoxColoringRecipeProviderFactory.getInstance();
    }

    private static final Map<DyeColor, Material> SHULKER_DYES = ImmutableMap.<DyeColor, Material>builder()
        .put(DyeColor.BLACK, Material.BLACK_SHULKER_BOX)
        .put(DyeColor.BLUE, Material.BLUE_SHULKER_BOX)
        .put(DyeColor.BROWN, Material.BROWN_SHULKER_BOX)
        .put(DyeColor.CYAN, Material.CYAN_SHULKER_BOX)
        .put(DyeColor.GRAY, Material.GRAY_SHULKER_BOX)
        .put(DyeColor.GREEN, Material.GREEN_SHULKER_BOX)
        .put(DyeColor.LIGHT_BLUE, Material.LIGHT_BLUE_SHULKER_BOX)
        .put(DyeColor.LIGHT_GRAY, Material.LIGHT_GRAY_SHULKER_BOX)
        .put(DyeColor.LIME, Material.LIME_SHULKER_BOX)
        .put(DyeColor.MAGENTA, Material.MAGENTA_SHULKER_BOX)
        .put(DyeColor.ORANGE, Material.ORANGE_SHULKER_BOX)
        .put(DyeColor.PINK, Material.PINK_SHULKER_BOX)
        .put(DyeColor.PURPLE, Material.PURPLE_SHULKER_BOX)
        .put(DyeColor.RED, Material.RED_SHULKER_BOX)
        .put(DyeColor.WHITE, Material.WHITE_SHULKER_BOX)
        .put(DyeColor.YELLOW, Material.YELLOW_SHULKER_BOX)
        .build();

    private ShulkerBoxColoringRecipeProvider(String namespace, String key) {
        super(
            ShulkerBoxColoringRecipeInput.class,
            new NamespacedKey(namespace, key)
        );
    }

    @Override
    public Optional<Recipe> getRecipeFor(ShulkerBoxColoringRecipeInput input) {
        ItemStack shulker = null;
        DyeColor dye = null;

        for (ItemStack item : input.getInput()) {
            if (itemStackIsEmpty(item)) {
                continue;
            }

            if (MaterialTags.DYES.isTagged(item.getType())) {
                if (dye != null) {
                    return Optional.empty(); // Can't combine dyes
                }
                dye = DyeUtils.getDyeColor(item.getType());
                continue;
            }

            if (MaterialTags.SHULKER_BOXES.isTagged(item.getType())) {
                if (shulker != null) {
                    return Optional.empty(); // Can't dye more than one item
                }
                shulker = item;
                continue;
            }

            return Optional.empty(); // Non-matching item
        }

        if (shulker == null) {
            return Optional.empty(); // No shulker
        }

        if (dye == null) {
            return Optional.empty(); // No color
        }

        ItemStack ret = shulker.clone();
        ret.setType(SHULKER_DYES.get(dye));

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

    public static class ShulkerBoxColoringRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<ShulkerBoxColoringRecipeProvider, ShulkerBoxColoringRecipe> {
        private static volatile ShulkerBoxColoringRecipeProviderFactory instance = null;

        private ShulkerBoxColoringRecipeProviderFactory() {
            super(ShulkerBoxColoringRecipe.class, ShulkerBoxColoringRecipeProvider.class, ShulkerBoxColoringRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ ShulkerBoxColoringRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static ShulkerBoxColoringRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (ShulkerBoxColoringRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new ShulkerBoxColoringRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
