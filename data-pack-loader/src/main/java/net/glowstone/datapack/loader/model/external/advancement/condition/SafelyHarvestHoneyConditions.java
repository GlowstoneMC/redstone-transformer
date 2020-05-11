package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Block;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;

import java.util.Optional;

public class SafelyHarvestHoneyConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:safely_harvest_honey";

    private final Optional<Block> block;
    private final Optional<Item> item;

    @JsonCreator
    public SafelyHarvestHoneyConditions(
        @JsonProperty("block") Optional<Block> block,
        @JsonProperty("item") Optional<Item> item) {
        this.block = block;
        this.item = item;
    }

    public Optional<Block> getBlock() {
        return block;
    }

    public Optional<Item> getItem() {
        return item;
    }
}
