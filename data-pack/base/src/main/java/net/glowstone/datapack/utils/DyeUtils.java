package net.glowstone.datapack.utils;

import com.google.common.collect.ImmutableMap;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import java.util.Map;
import java.util.Optional;

public class DyeUtils {
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

    public static DyeColor getDyeColor(Material material) {
        return DYE_COLORS.get(material);
    }
}
