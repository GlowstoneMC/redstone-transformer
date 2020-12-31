package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.recipes.StaticResultRecipe;
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

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class RepairItemRecipeProvider extends DynamicRecipeProvider<RepairItemRecipeInput> {
    private static boolean isRepairable(ItemStack item) {
        return item.getItemMeta() instanceof Damageable;
    }

    public RepairItemRecipeProvider(String namespace, String key) {
        super(RepairItemRecipeInput.class, new NamespacedKey(namespace, key));
    }

    @Override
    public Optional<Recipe> getRecipeFor(RepairItemRecipeInput input) {
        List<ItemStack> items = new ArrayList<>();

        for (ItemStack item : input.getInput()) {
            if (itemStackIsEmpty(item)) {
                continue;
            }

            if (!isRepairable(item)) {
                return Optional.empty(); // Non-repairable item in matrix
            }

            items.add(item);
        }

        if (items.size() != 2) {
            return Optional.empty(); // Can only have 2 tools
        }

        ItemStack itemA = items.get(0);
        ItemStack itemB = items.get(1);

        if (itemA.getType() != itemB.getType()) {
            return Optional.empty(); // Not same item type
        }

        Material type = itemA.getType();

        int usesA = type.getMaxDurability() - ((Damageable)itemA.getItemMeta()).getDamage();
        int usesB = type.getMaxDurability() - ((Damageable)itemB.getItemMeta()).getDamage();
        int totalUses = (int) (usesA + usesB + type.getMaxDurability() * 0.05);
        int damage = type.getMaxDurability() - totalUses;

        ItemMeta newItemMeta = Bukkit.getItemFactory().getItemMeta(type);
        ((Damageable)newItemMeta).setDamage(damage);
        ItemStack newItem = new ItemStack(type);
        newItem.setItemMeta(newItemMeta);

        return Optional.of(new StaticResultRecipe(getKey(), newItem));
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
