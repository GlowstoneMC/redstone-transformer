package net.glowstone.datapack.recipes.providers;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.collect.ImmutableMap;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.BookCloningRecipeInput;
import net.glowstone.datapack.recipes.inputs.ShulkerBoxColoringRecipeInput;
import net.glowstone.datapack.utils.DyeUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class BookCloningRecipeProvider extends DynamicRecipeProvider<BookCloningRecipeInput> {
    public BookCloningRecipeProvider(String namespace, String key) {
        super(
            BookCloningRecipeInput.class,
            new NamespacedKey(namespace, key)
        );
    }

    @Override
    public Optional<Recipe> getRecipeFor(BookCloningRecipeInput input) {
        ItemStack original = null;
        int clones = 0;

        for (ItemStack item : input.getInput()) {
            if (itemStackIsEmpty(item)) {
                continue;
            }

            if (Material.WRITTEN_BOOK.equals(item.getType())) {
                if (original != null) {
                    return Optional.empty(); // Can't combine books
                }
                original = item;
                continue;
            }

            if (Material.WRITABLE_BOOK.equals(item.getType())) {
                clones++;
                continue;
            }

            return Optional.empty(); // Non-matching item
        }

        if (original == null) {
            return Optional.empty(); // No written book
        }

        if (clones == 0) {
            return Optional.empty(); // No writable books
        }

        ItemStack ret = original.clone();
        ret.setAmount(clones);

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
