package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class ItemDurabilityChangedConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:item_durability_changed";

    private final Optional<RangedInt> delta;
    private final Optional<RangedInt> durability;
    private final Optional<Item> item;

    @JsonCreator
    public ItemDurabilityChangedConditions(
        @JsonProperty("delta") Optional<RangedInt> delta,
        @JsonProperty("durability") Optional<RangedInt> durability,
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.delta = delta;
        this.durability = durability;
        this.item = item;
    }

    public Optional<RangedInt> getDelta() {
        return delta;
    }

    public Optional<RangedInt> getDurability() {
        return durability;
    }

    public Optional<Item> getItem() {
        return item;
    }
}
