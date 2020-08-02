package net.glowstone.datapack.recipes;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.tags.ExtraMaterialTags;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ArmorDyeRecipeProvider extends AbstractRecipeProvider<CraftingInventory> {
    private static final Map<Material, DyeColor> DYE_COLORS = ImmutableMap.<Material, DyeColor>builder()
        .put(Material.BLACK_DYE, DyeColor.BLACK)
        .put(Material.BLUE_DYE, DyeColor.BLUE)
        .put(Material.BROWN_DYE, DyeColor.BROWN)
        .put(Material.CYAN_DYE, DyeColor.CYAN)
        .put(Material.GRAY_DYE, DyeColor.GRAY)
        .put(Material.GREEN_DYE, DyeColor.GREEN)
        .put(Material.LIGHT_BLUE_DYE, DyeColor.LIGHT_BLUE)
        .put(Material.LIGHT_GRAY_DYE, DyeColor.LIGHT_GRAY)
        .put(Material.LIME_DYE, DyeColor.LIME)
        .put(Material.MAGENTA_DYE, DyeColor.MAGENTA)
        .put(Material.ORANGE_DYE, DyeColor.ORANGE)
        .put(Material.PINK_DYE, DyeColor.PINK)
        .put(Material.PURPLE_DYE, DyeColor.PURPLE)
        .put(Material.RED_DYE, DyeColor.RED)
        .put(Material.WHITE_DYE, DyeColor.WHITE)
        .put(Material.YELLOW_DYE, DyeColor.YELLOW)
        .build();

    private final NamespacedKey key;

    public ArmorDyeRecipeProvider(String namespace, String key) {
        super(CraftingInventory.class);
        this.key = new NamespacedKey(namespace, key);
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    @Override
    public Optional<Recipe> getRecipeFor(CraftingInventory inventory) {
        ItemStack armor = null;
        List<Color> colors = new ArrayList<>();

        for (ItemStack item : inventory.getMatrix()) {
            if (itemStackIsEmpty(item)) {
                continue;
            }

            if (MaterialTags.DYES.isTagged(item.getType())) {
                Color color = DYE_COLORS.get(item.getType()).getColor();
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

        return Optional.of(new StaticResultRecipe(ret));
    }
}
