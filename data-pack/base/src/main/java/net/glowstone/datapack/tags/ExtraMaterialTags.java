package net.glowstone.datapack.tags;

import com.destroystokyo.paper.MaterialSetTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.data.Bisected;

public class ExtraMaterialTags {
    private static NamespacedKey keyFor(String key) {
        //noinspection deprecation
        return new NamespacedKey("glowstone", key + "_settag");
    }

    public static final Tag<Material> DYABLE_ARMOR = new MaterialSetTag(keyFor("dyeable_armor"))
        .startsWith("LEATHER_")
        .ensureSize("DYEABLE_ARMOR", 5);

    public static final Tag<Material> WOODS = new MaterialSetTag(keyFor("woods"))
        .endsWith("_WOOD")
        .notStartsWith("STRIPPED_")
        .ensureSize("WOODS", 6);

    public static final Tag<Material> AIR_VARIANTS = new MaterialSetTag(keyFor("air_variants"))
        .endsWith("_AIR")
        .add(Material.AIR)
        .add(Material.STRUCTURE_VOID)
        .ensureSize("AIR_VARIANTS", 4);

    public static final Tag<Material> BISECTED_BLOCKS = new MaterialSetTag(keyFor("bisected_blocks"))
        .add(mat -> Bisected.class.isAssignableFrom(mat.getData()));

    public static final Tag<Material> HEADS = new MaterialSetTag(keyFor("heads"))
        .endsWith("_HEAD")
        .notEndsWith("_WALL_HEAD")
        .not(Material.PISTON_HEAD)
        .ensureSize("HEADS", 4);
}
