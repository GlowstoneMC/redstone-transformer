package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.FireworkStarRecipeInput;
import net.glowstone.datapack.recipes.inputs.MapCloningRecipeInput;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class FireworkRocketRecipeProvider extends DynamicRecipeProvider<FireworkStarRecipeInput> {
    public FireworkRocketRecipeProvider(String namespace, String key) {
        super(FireworkStarRecipeInput.class, new NamespacedKey(namespace, key));
    }

    @Override
    public Optional<Recipe> getRecipeFor(FireworkStarRecipeInput input) {
        List<FireworkEffect> effects = new ArrayList<>();
        boolean paper = false;
        int duration = 0;

        for (ItemStack itemStack : input.getInput()) {
            if (itemStackIsEmpty(itemStack)) {
                continue;
            }
            if (itemStack.getType() == Material.PAPER) {
                if (paper) {
                    return Optional.empty(); // Can't have more than one paper
                }
                paper = true;
            }
            if (itemStack.getType() == Material.GUNPOWDER) {
                duration += 1;
                continue;
            }
            if (itemStack.getType() == Material.FIREWORK_STAR) {
                effects.add(((FireworkEffectMeta) itemStack.getItemMeta()).getEffect());
                continue;
            }

            return Optional.empty(); // Unmatched item
        }

        if (!paper || duration == 0 || duration > 3) {
            return Optional.empty(); // Missing paper or gunpowder, or too much gunpowder
        }

        ItemStack ret = new ItemStack(Material.FIREWORK_ROCKET, 3);
        FireworkMeta meta = (FireworkMeta) ret.getItemMeta();
        meta.setPower(duration);
        meta.addEffects(effects);

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
