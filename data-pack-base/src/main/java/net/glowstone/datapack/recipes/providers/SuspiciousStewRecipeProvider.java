package net.glowstone.datapack.recipes.providers;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.ArmorDyeRecipeInput;
import net.glowstone.datapack.recipes.inputs.SuspiciousStewRecipeInput;
import net.glowstone.datapack.tags.ExtraMaterialTags;
import net.glowstone.datapack.utils.TickUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class SuspiciousStewRecipeProvider extends DynamicRecipeProvider<SuspiciousStewRecipeInput> {
    // Need to use supplier functions because createEffect produces a null pointer exception otherwise
    private static final Map<Material, Supplier<PotionEffect>> FLOWER_EFFECTS = ImmutableMap.<Material, Supplier<PotionEffect>>builder()
        .put(Material.ALLIUM, () -> PotionEffectType.FIRE_RESISTANCE.createEffect(TickUtils.secondsToTicks(4), 1))
        .put(Material.AZURE_BLUET, () -> PotionEffectType.BLINDNESS.createEffect(TickUtils.secondsToTicks(8), 1))
        .put(Material.BLUE_ORCHID, () -> PotionEffectType.SATURATION.createEffect(TickUtils.millisecondsToTicks(350), 1))
        .put(Material.DANDELION, () -> PotionEffectType.SATURATION.createEffect(TickUtils.millisecondsToTicks(350), 1))
        .put(Material.CORNFLOWER, () -> PotionEffectType.JUMP.createEffect(TickUtils.secondsToTicks(6), 1))
        .put(Material.LILY_OF_THE_VALLEY, () -> PotionEffectType.POISON.createEffect(TickUtils.secondsToTicks(12), 1))
        .put(Material.OXEYE_DAISY, () -> PotionEffectType.REGENERATION.createEffect(TickUtils.secondsToTicks(8), 1))
        .put(Material.POPPY, () -> PotionEffectType.NIGHT_VISION.createEffect(TickUtils.secondsToTicks(5), 1))
        .put(Material.RED_TULIP, () -> PotionEffectType.WEAKNESS.createEffect(TickUtils.secondsToTicks(9), 1))
        .put(Material.ORANGE_TULIP, () -> PotionEffectType.WEAKNESS.createEffect(TickUtils.secondsToTicks(9), 1))
        .put(Material.PINK_TULIP, () -> PotionEffectType.WEAKNESS.createEffect(TickUtils.secondsToTicks(9), 1))
        .put(Material.WHITE_TULIP, () -> PotionEffectType.WEAKNESS.createEffect(TickUtils.secondsToTicks(9), 1))
        .put(Material.WITHER_ROSE, () -> PotionEffectType.WITHER.createEffect(TickUtils.secondsToTicks(8), 1))
        .build();

    public SuspiciousStewRecipeProvider(String namespace, String key) {
        super(
            SuspiciousStewRecipeInput.class,
            new NamespacedKey(namespace, key)
        );
    }

    @Override
    public Optional<Recipe> getRecipeFor(SuspiciousStewRecipeInput input) {
        int redMushrooms = 0;
        int brownMushrooms = 0;
        int bowls = 0;
        List<PotionEffect> potionEffects = new ArrayList<>();

        for (ItemStack item : input.getInput()) {
            if (itemStackIsEmpty(item)) {
                continue;
            }

            if (Material.RED_MUSHROOM.equals(item.getType())) {
                redMushrooms++;
                continue;
            }

            if (Material.BROWN_MUSHROOM.equals(item.getType())) {
                brownMushrooms++;
                continue;
            }

            if (Material.BOWL.equals(item.getType())) {
                bowls++;
                continue;
            }

            if (FLOWER_EFFECTS.containsKey(item.getType())) {
                potionEffects.add(FLOWER_EFFECTS.get(item.getType()).get());
                continue;
            }

            return Optional.empty(); // Item mismatch
        }

        if (redMushrooms != 1 || brownMushrooms != 1 || bowls != 1 || potionEffects.size() != 1) {
            return Optional.empty(); // Too many items provided
        }

        SuspiciousStewMeta meta = (SuspiciousStewMeta) Bukkit.getItemFactory().getItemMeta(Material.SUSPICIOUS_STEW);
        meta.addCustomEffect(potionEffects.get(0), true);

        ItemStack ret = new ItemStack(Material.SUSPICIOUS_STEW);
        ret.setItemMeta(meta);

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
