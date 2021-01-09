package net.glowstone.datapack.recipes;

import net.glowstone.datapack.tags.HashObservableSet;
import net.glowstone.datapack.tags.ObservableSet;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MaterialTagRecipeChoice implements RecipeChoice {
    private final ObservableSet<Material> choices;

    public MaterialTagRecipeChoice(Keyed... keyeds) {
        this(Arrays.asList(keyeds));
    }

    public MaterialTagRecipeChoice(Collection<Keyed> keyeds) {
        Set<Material> values = new HashSet<>();
        Set<SubTagTrackingTag<Material>> tags = new HashSet<>();
        for (Keyed keyed : keyeds) {
            if (keyed instanceof Material) {
                values.add((Material) keyed);
            } else if (keyed instanceof SubTagTrackingTag) {
                SubTagTrackingTag<?> tag = (SubTagTrackingTag<?>) keyed;
                if (Material.class.isAssignableFrom(tag.getValueClass())) {
                    @SuppressWarnings("unchecked")
                    SubTagTrackingTag<Material> materialTag = (SubTagTrackingTag<Material>) tag;
                    tags.add(materialTag);
                } else {
                    throw new IllegalStateException("All SubTagTrackingTags must contain Materials.");
                }
            } else {
                throw new IllegalStateException("Keyed objects must be either Materials or SubTagTrackingTags.");
            }
        }
        this.choices = new HashObservableSet<>(Material.class, values, tags);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialTagRecipeChoice that = (MaterialTagRecipeChoice) o;
        return choices.equals(that.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choices);
    }
}
