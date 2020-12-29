package net.glowstone.datapack.recipes.providers;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.ShieldDecorationRecipeInput;
import net.glowstone.datapack.recipes.inputs.ShulkerBoxColoringRecipeInput;
import net.glowstone.datapack.utils.DyeUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ShieldDecorationRecipeProvider extends DynamicRecipeProvider<ShieldDecorationRecipeInput> {
    public ShieldDecorationRecipeProvider(String namespace, String key) {
        super(
            ShieldDecorationRecipeInput.class,
            new NamespacedKey(namespace, key)
        );
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
}
