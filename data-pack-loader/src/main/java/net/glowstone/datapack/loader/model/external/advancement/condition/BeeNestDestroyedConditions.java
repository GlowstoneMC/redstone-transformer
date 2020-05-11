package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;

import java.util.Optional;
import java.util.OptionalInt;

public class BeeNestDestroyedConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:bee_nest_destroyed";

    private final Optional<String> block;
    private final Optional<Item> item;
    private final OptionalInt numBeesInside;

    @JsonCreator
    public BeeNestDestroyedConditions(
        @JsonProperty("block") Optional<String> block,
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("num_bees_inside") OptionalInt numBeesInside) {
        this.block = block;
        this.item = item;
        this.numBeesInside = numBeesInside;
    }

    public Optional<String> getBlock() {
        return block;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public OptionalInt getNumBeesInside() {
        return numBeesInside;
    }
}
