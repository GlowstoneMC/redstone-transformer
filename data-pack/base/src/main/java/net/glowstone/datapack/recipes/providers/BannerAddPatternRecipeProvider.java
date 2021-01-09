package net.glowstone.datapack.recipes.providers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.BannerAddPaternRecipeInput;
import net.glowstone.datapack.utils.BannerPatternUtils.ItemTag;
import net.glowstone.datapack.utils.DyeUtils;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BannerMeta;

import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.BANNER;
import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.DYE;
import static net.glowstone.datapack.utils.BannerPatternUtils.getPatternType;

public class BannerAddPatternRecipeProvider extends DynamicRecipeProvider<BannerAddPaternRecipeInput> {
    public BannerAddPatternRecipeProvider(String namespace, String key) {
        super(
            BannerAddPaternRecipeInput.class,
            new NamespacedKey(namespace, key)
        );
    }

    @Override
    public Optional<Recipe> getRecipeFor(BannerAddPaternRecipeInput input) {
        if (input.getInput().length != 9) {
            return Optional.empty(); // Not big enough
        }

        List<ItemTag> patternRecipe = new ArrayList<>(9);
        ItemStack dye = null;
        ItemStack banner = null;

        for (ItemStack itemStack : input.getInput()) {
            Optional<ItemTag> itemTag;
            itemTag = ItemTag.convert(itemStack);

            if (itemTag.isPresent()) {
                patternRecipe.add(itemTag.get());

                if (itemTag.get() == DYE) {
                    if (dye == null) {
                        dye = itemStack;
                    } else if (dye.getType() != itemStack.getType()) {
                        return Optional.empty(); // All dyes must be the same
                    }
                } else if (itemTag.get() == BANNER) {
                    if (banner == null) {
                        banner = itemStack;
                    } else {
                        return Optional.empty(); // Can't have two banners.
                    }
                }
            } else {
                return Optional.empty(); // Unknown ItemTag
            }
        }

        Optional<PatternType> patternType = getPatternType(patternRecipe);

        if (!patternType.isPresent() || dye == null || banner == null) {
            return Optional.empty(); // Unknown recipe
        }

        DyeColor dyeColor = DyeUtils.getDyeColor(dye.getType());
        Pattern pattern = new Pattern(dyeColor, patternType.get());

        ItemStack ret = banner.clone();
        ret.setAmount(1);
        ((BannerMeta) ret.getItemMeta()).addPattern(pattern);

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
