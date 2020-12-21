package net.glowstone.datapack.tags;

import com.destroystokyo.paper.MaterialSetTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class ExtraMaterialTags {
    private static NamespacedKey keyFor(String key) {
        //noinspection deprecation
        return new NamespacedKey("glowstone", key + "_settag");
    }

    public static final MaterialSetTag DYABLE_ARMOR = new MaterialSetTag(keyFor("dyable_armor"))
        .add(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS);
}
