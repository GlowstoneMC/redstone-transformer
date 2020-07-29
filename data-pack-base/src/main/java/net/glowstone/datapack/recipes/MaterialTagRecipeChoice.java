package net.glowstone.datapack.recipes;

import net.glowstone.datapack.tags.HashObservableSet;
import net.glowstone.datapack.tags.ObservableSet;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.util.Set;

public class MaterialTagRecipeChoice implements RecipeChoice {
    private final ObservableSet<Material> choices;

    public MaterialTagRecipeChoice(Set<Material> directValues, Set<SubTagTrackingTag<Material>> subTags) {
        this.choices = new HashObservableSet<>(Material.class, directValues, subTags);
    }

    private MaterialTagRecipeChoice(ObservableSet<Material> choices) {
        this.choices = choices;
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack stack = new ItemStack(choices.iterator().next());

        // For compat
        if (choices.size() > 1) {
            stack.setDurability(Short.MAX_VALUE);
        }

        return stack;
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public RecipeChoice clone() {
        return new MaterialTagRecipeChoice(this.choices.clone());
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return choices.contains(itemStack.getType());
    }
}
