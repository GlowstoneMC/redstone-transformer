package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Location;
import net.glowstone.datapack.loader.model.external.recipe.Item;

import java.util.Map;
import java.util.Optional;

public class PlacedBlockConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:placed_block";

    private final Optional<String> block;
    private final Optional<Item> item;
    private final Optional<Location> location;
    private final Optional<Map<String, String>> state;

    @JsonCreator
    public PlacedBlockConditions(
        @JsonProperty("block") Optional<String> block,
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("location") Optional<Location> location,
        @JsonProperty("state") Optional<Map<String, String>> state) {
        this.block = block;
        this.item = item;
        this.location = location;
        this.state = state;
    }

    public Optional<String> getBlock() {
        return block;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public Optional<Location> getLocation() {
        return location;
    }

    public Optional<Map<String, String>> getState() {
        return state;
    }
}
