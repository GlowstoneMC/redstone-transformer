package net.glowstone.datapack.recipes.inputs;

import com.google.common.collect.ImmutableSet;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeInputRegistry {
    private static final Set<InventoryConverter> INVENTORY_FUNCS =
        ImmutableSet.<InventoryConverter>builder()
            .add(ArmorDyeRecipeInput::create)
            .add(BannerAddPaternRecipeInput::create)
            .add(BannerDuplicateRecipeInput::create)
            .add(BlastingRecipeInput::create)
            .add(BookCloningRecipeInput::create)
            .add(FireworkRocketRecipeInput::create)
            .add(FireworkStarFadeRecipeInput::create)
            .add(FireworkStarRecipeInput::create)
            .add(FurnaceRecipeInput::create)
            .add(MapCloningRecipeInput::create)
            .add(MapExtendingRecipeInput::create)
            .add(RepairItemRecipeInput::create)
            .add(ShapedRecipeInput::create)
            .add(ShapelessRecipeInput::create)
            .add(ShieldDecorationRecipeInput::create)
            .add(ShulkerBoxColoringRecipeInput::create)
            .add(SmokingRecipeInput::create)
            .add(StonecuttingRecipeInput::create)
            .add(SuspiciousStewRecipeInput::create)
            .add(TippedArrowRecipeInput::create)
            .build();

    private static final Set<InventoryTypeConverter> INVENTORY_TYPE_FUNCS =
        ImmutableSet.<InventoryTypeConverter>builder()
            .add(ArmorDyeRecipeInput::create)
            .add(BannerAddPaternRecipeInput::create)
            .add(BannerDuplicateRecipeInput::create)
            .add(BlastingRecipeInput::create)
            .add(BookCloningRecipeInput::create)
            .add(FireworkRocketRecipeInput::create)
            .add(FireworkStarFadeRecipeInput::create)
            .add(FireworkStarRecipeInput::create)
            .add(FurnaceRecipeInput::create)
            .add(MapCloningRecipeInput::create)
            .add(MapExtendingRecipeInput::create)
            .add(RepairItemRecipeInput::create)
            .add(ShapedRecipeInput::create)
            .add(ShapelessRecipeInput::create)
            .add(ShieldDecorationRecipeInput::create)
            .add(ShulkerBoxColoringRecipeInput::create)
            .add(SmokingRecipeInput::create)
            .add(StonecuttingRecipeInput::create)
            .add(SuspiciousStewRecipeInput::create)
            .add(TippedArrowRecipeInput::create)
            .build();

    public static List<RecipeInput> from(Inventory inventory) {
        return INVENTORY_FUNCS.stream()
            .map((func) -> func.create(inventory))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    public static List<RecipeInput> from(InventoryType inventoryType, ItemStack[] itemStacks) {
        return INVENTORY_TYPE_FUNCS.stream()
            .map((func) -> func.create(inventoryType, itemStacks))
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
