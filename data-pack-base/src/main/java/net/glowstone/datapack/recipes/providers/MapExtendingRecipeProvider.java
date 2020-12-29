package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.MapExtendingRecipeInput;
import net.glowstone.datapack.recipes.inputs.RepairItemRecipeInput;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MapExtendingRecipeProvider extends DynamicRecipeProvider<MapExtendingRecipeInput> {
    private static final List<Material> RECIPE = ImmutableList.<Material>builder()
        .add(Material.PAPER, Material.PAPER, Material.PAPER)
        .add(Material.PAPER, Material.FILLED_MAP, Material.PAPER)
        .add(Material.PAPER, Material.PAPER, Material.PAPER)
        .build();

    public MapExtendingRecipeProvider(String namespace, String key) {
        super(MapExtendingRecipeInput.class, new NamespacedKey(namespace, key));
    }

    @Override
    public Optional<Recipe> getRecipeFor(MapExtendingRecipeInput input) {
        if (input.getInput().length != RECIPE.size()) {
            return Optional.empty(); // Not big enough
        }

        ItemStack map = null;

        for (int i = 0; i < RECIPE.size(); i++) {
            ItemStack item = input.getInput()[i];

            if (itemStackIsEmpty(item)) {
                return Optional.empty(); // No stacks can be empty
            }

            if (item.getType() != RECIPE.get(i)) {
                return Optional.empty(); // Item doesn't match recipe.
            }

            if (item.getType() == Material.FILLED_MAP) {
                map = item;
            }
        }

        if (map == null) {
            return Optional.empty(); // Sanity check, should never happen.
        }

        //TODO: Add zooming once maps are implemented

        return Optional.of(new StaticResultRecipe(getKey(), map.clone()));
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
