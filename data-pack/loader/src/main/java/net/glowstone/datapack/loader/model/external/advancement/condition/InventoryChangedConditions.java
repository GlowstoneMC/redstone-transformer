package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Slots;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class InventoryChangedConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:inventory_changed";

    private final Optional<List<Item>> items;
    private final Optional<Slots> slots;

    @JsonCreator
    public InventoryChangedConditions(
        @JsonProperty("items") Optional<List<Item>> items,
        @JsonProperty("slots") Optional<Slots> slots,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.items = items;
        this.slots = slots;
    }

    public Optional<List<Item>> getItems() {
        return items;
    }

    public Optional<Slots> getSlots() {
        return slots;
    }
}
