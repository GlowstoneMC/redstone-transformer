package net.glowstone.datapack.recipes.inputs;

import com.google.common.collect.ImmutableSet;
import net.glowstone.datapack.recipes.inputs.RecipeInput.InventoryRecipeInputFactory;
import net.glowstone.datapack.recipes.inputs.RecipeInput.InventoryTypeRecipeInputFactory;
import net.glowstone.datapack.recipes.inputs.RecipeInput.RecipeInputFactory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeInputRegistry {
    private static final Set<RecipeInputFactory<?>> ALL_FACTORIES =
        ImmutableSet.<RecipeInputFactory<?>>builder()
            .add(ArmorDyeRecipeInput.factory())
            .add(BannerAddPatternRecipeInput.factory())
            .add(BannerDuplicateRecipeInput.factory())
            .add(BlastingRecipeInput.factory())
            .add(BookCloningRecipeInput.factory())
            .add(CampfireRecipeInput.factory())
            .add(FireworkRocketRecipeInput.factory())
            .add(FireworkStarFadeRecipeInput.factory())
            .add(FireworkStarRecipeInput.factory())
            .add(FurnaceRecipeInput.factory())
            .add(MapCloningRecipeInput.factory())
            .add(MapExtendingRecipeInput.factory())
            .add(RepairItemRecipeInput.factory())
            .add(ShapedRecipeInput.factory())
            .add(ShapelessRecipeInput.factory())
            .add(ShieldDecorationRecipeInput.factory())
            .add(ShulkerBoxColoringRecipeInput.factory())
            .add(SmithingRecipeInput.factory())
            .add(SmokingRecipeInput.factory())
            .add(StonecuttingRecipeInput.factory())
            .add(SuspiciousStewRecipeInput.factory())
            .add(TippedArrowRecipeInput.factory())
            .build();

    private static final Set<InventoryRecipeInputFactory<?>> INVENTORY_FACTORIES =
        ALL_FACTORIES.stream()
            .filter((factory) -> factory instanceof InventoryRecipeInputFactory)
            .map((factory) -> (InventoryRecipeInputFactory<?>) factory)
            .collect(Collectors.toSet());

    private static final Set<InventoryTypeRecipeInputFactory<?>> INVENTORY_TYPE_FUNCS =
        ALL_FACTORIES.stream()
            .filter((factory) -> factory instanceof InventoryTypeRecipeInputFactory)
            .map((factory) -> (InventoryTypeRecipeInputFactory<?>) factory)
            .collect(Collectors.toSet());

    public static List<RecipeInput> from(Inventory inventory) {
        return INVENTORY_FACTORIES.stream()
            .map((factory) -> factory.create(inventory))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    public static List<RecipeInput> from(InventoryType inventoryType, ItemStack[] itemStacks) {
        return INVENTORY_TYPE_FUNCS.stream()
            .map((factory) -> factory.create(inventoryType, itemStacks))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    @FunctionalInterface
    private interface InventoryConverter {
        Optional<? extends RecipeInput> create(Inventory inventory);
    }

    @FunctionalInterface
    private interface InventoryTypeConverter {
        Optional<? extends RecipeInput> create(InventoryType inventoryType, ItemStack[] itemStacks);
    }
}
