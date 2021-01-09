package net.glowstone.datapack.utils;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.EMPTY;
import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.DYE;
import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.BANNER;
import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.VINE;
import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.BRICKS;
import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.CREEPER_HEAD;
import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.WITHER_SKELETON_SKULL;
import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.OXEYE_DAISY;
import static net.glowstone.datapack.utils.BannerPatternUtils.ItemTag.ENCHANTED_GOLDEN_APPLE;
import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class BannerPatternUtils {
    public enum ItemTag {
        EMPTY(),
        DYE(MaterialTags.DYES),
        BANNER(Tag.ITEMS_BANNERS),
        VINE(Material.VINE),
        BRICKS(Material.BRICKS),
        CREEPER_HEAD(Material.CREEPER_HEAD),
        WITHER_SKELETON_SKULL(Material.WITHER_SKELETON_SKULL),
        OXEYE_DAISY(Material.OXEYE_DAISY),
        ENCHANTED_GOLDEN_APPLE(Material.ENCHANTED_GOLDEN_APPLE),
        ;

        private static final Map<Material, ItemTag> MATERIALS;
        private static final Map<Tag<Material>, ItemTag> TAGS;

        static {
            ImmutableMap.Builder<Material, ItemTag> materials = ImmutableMap.builder();
            ImmutableMap.Builder<Tag<Material>, ItemTag> tags = ImmutableMap.builder();

            for (ItemTag itemTag : values()) {
                if (itemTag.getMaterial().isPresent()) {
                    materials.put(itemTag.getMaterial().get(), itemTag);
                }
                if (itemTag.getTag().isPresent()) {
                    tags.put(itemTag.getTag().get(), itemTag);
                }
            }

            MATERIALS = materials.build();
            TAGS = tags.build();
        }

        public static Optional<ItemTag> convert(ItemStack itemStack) {
            if (itemStackIsEmpty(itemStack)) {
                return Optional.of(EMPTY);
            }
            if (MATERIALS.containsKey(itemStack.getType())) {
                return Optional.of(MATERIALS.get(itemStack.getType()));
            }
            for (Map.Entry<Tag<Material>, ItemTag> entry : TAGS.entrySet()) {
                if (entry.getKey().isTagged(itemStack.getType())) {
                    return Optional.of(entry.getValue());
                }
            }
            return Optional.empty();
        }

        private final Optional<Material> material;
        private final Optional<Tag<Material>> tag;

        private ItemTag() {
            this.material = Optional.empty();
            this.tag = Optional.empty();
        }

        private ItemTag(Material material) {
            this.material = Optional.of(material);
            this.tag = Optional.empty();
        }

        private ItemTag(Tag<Material> tag) {
            this.material = Optional.empty();
            this.tag = Optional.of(tag);
        }

        public Optional<Material> getMaterial() {
            return material;
        }

        public Optional<Tag<Material>> getTag() {
            return tag;
        }
    }

    private static final Map<List<ItemTag>, PatternType> PATTERN_RECIPES =
        ImmutableMap.<List<ItemTag>, PatternType>builder()
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    EMPTY, BANNER, EMPTY,
                    DYE, DYE, DYE
                ),
                PatternType.STRIPE_BOTTOM
            )
            .put(
                ImmutableList.of(
                    DYE, DYE, DYE,
                    EMPTY, EMPTY, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.STRIPE_TOP
            )
            .put(
                ImmutableList.of(
                    DYE, EMPTY, EMPTY,
                    DYE, EMPTY, EMPTY,
                    DYE, BANNER, EMPTY
                ),
                PatternType.STRIPE_LEFT
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, DYE,
                    EMPTY, EMPTY, DYE,
                    EMPTY, BANNER, DYE
                ),
                PatternType.STRIPE_RIGHT
            )
            .put(
                ImmutableList.of(
                    EMPTY, DYE, EMPTY,
                    EMPTY, DYE, BANNER,
                    EMPTY, DYE, EMPTY
                ),
                PatternType.STRIPE_CENTER
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    DYE, DYE, DYE,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.STRIPE_MIDDLE
            )
            .put(
                ImmutableList.of(
                    DYE, EMPTY, EMPTY,
                    EMPTY, DYE, EMPTY,
                    EMPTY, BANNER, DYE
                ),
                PatternType.STRIPE_DOWNRIGHT
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, DYE,
                    EMPTY, DYE, EMPTY,
                    DYE, BANNER, EMPTY
                ),
                PatternType.STRIPE_DOWNLEFT
            )
            .put(
                ImmutableList.of(
                    DYE, EMPTY, DYE,
                    DYE, EMPTY, DYE,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.STRIPE_SMALL
            )
            .put(
                ImmutableList.of(
                    DYE, EMPTY, DYE,
                    EMPTY, DYE, EMPTY,
                    DYE, BANNER, DYE
                ),
                PatternType.CROSS
            )
            .put(
                ImmutableList.of(
                    EMPTY, DYE, EMPTY,
                    DYE, DYE, DYE,
                    BANNER, DYE, EMPTY
                ),
                PatternType.STRAIGHT_CROSS
            )
            .put(
                ImmutableList.of(
                    DYE, DYE, EMPTY,
                    DYE, EMPTY, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.DIAGONAL_LEFT
            )
            .put(
                ImmutableList.of(
                    EMPTY, DYE, DYE,
                    EMPTY, EMPTY, DYE,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.DIAGONAL_RIGHT_MIRROR
            )
            .put(
                ImmutableList.of(
                    EMPTY, BANNER, EMPTY,
                    DYE, EMPTY, EMPTY,
                    DYE, DYE, EMPTY
                ),
                PatternType.DIAGONAL_LEFT_MIRROR
            )
            .put(
                ImmutableList.of(
                    EMPTY, BANNER, EMPTY,
                    EMPTY, EMPTY, DYE,
                    EMPTY, DYE, DYE
                ),
                PatternType.DIAGONAL_RIGHT
            )
            .put(
                ImmutableList.of(
                    DYE, DYE, EMPTY,
                    DYE, DYE, BANNER,
                    DYE, DYE, EMPTY
                ),
                PatternType.HALF_VERTICAL
            )
            .put(
                ImmutableList.of(
                    EMPTY, DYE, DYE,
                    BANNER, DYE, DYE,
                    EMPTY, DYE, DYE
                ),
                PatternType.HALF_VERTICAL_MIRROR
            )
            .put(
                ImmutableList.of(
                    DYE, DYE, DYE,
                    DYE, DYE, DYE,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.HALF_HORIZONTAL
            )
            .put(
                ImmutableList.of(
                    EMPTY, BANNER, EMPTY,
                    DYE, DYE, DYE,
                    DYE, DYE, DYE
                ),
                PatternType.HALF_HORIZONTAL_MIRROR
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    EMPTY, EMPTY, EMPTY,
                    DYE, BANNER, EMPTY
                ),
                PatternType.SQUARE_BOTTOM_LEFT
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    EMPTY, EMPTY, EMPTY,
                    EMPTY, BANNER, DYE
                ),
                PatternType.SQUARE_BOTTOM_RIGHT
            )
            .put(
                ImmutableList.of(
                    DYE, EMPTY, EMPTY,
                    EMPTY, EMPTY, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.SQUARE_TOP_LEFT
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, DYE,
                    EMPTY, EMPTY, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.SQUARE_TOP_RIGHT
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    EMPTY, DYE, EMPTY,
                    DYE, BANNER, DYE
                ),
                PatternType.TRIANGLE_BOTTOM
            )
            .put(
                ImmutableList.of(
                    DYE, EMPTY, DYE,
                    EMPTY, DYE, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.TRIANGLE_TOP
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    DYE, BANNER, DYE,
                    EMPTY, DYE, EMPTY
                ),
                PatternType.TRIANGLES_BOTTOM
            )
            .put(
                ImmutableList.of(
                    EMPTY, DYE, EMPTY,
                    DYE, EMPTY, DYE,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.TRIANGLES_TOP
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    EMPTY, DYE, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.CIRCLE_MIDDLE
            )
            .put(
                ImmutableList.of(
                    DYE, DYE, DYE,
                    DYE, BANNER, DYE,
                    DYE, DYE, DYE
                ),
                PatternType.BORDER
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    VINE, DYE, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.CURLY_BORDER
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    BRICKS, DYE, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.BRICKS
            )
            .put(
                ImmutableList.of(
                    DYE, BANNER, DYE,
                    EMPTY, DYE, EMPTY,
                    EMPTY, DYE, EMPTY
                ),
                PatternType.GRADIENT
            )
            .put(
                ImmutableList.of(
                    EMPTY, DYE, EMPTY,
                    EMPTY, DYE, EMPTY,
                    DYE, BANNER, DYE
                ),
                PatternType.GRADIENT_UP
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    CREEPER_HEAD, DYE, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.CREEPER
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    WITHER_SKELETON_SKULL, DYE, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.SKULL
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    OXEYE_DAISY, DYE, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.FLOWER
            )
            .put(
                ImmutableList.of(
                    EMPTY, EMPTY, EMPTY,
                    ENCHANTED_GOLDEN_APPLE, DYE, EMPTY,
                    EMPTY, BANNER, EMPTY
                ),
                PatternType.MOJANG
            )
            .build();

    public static Optional<PatternType> getPatternType(List<ItemTag> itemTags) {
        return Optional.ofNullable(PATTERN_RECIPES.get(itemTags));
    }
}
