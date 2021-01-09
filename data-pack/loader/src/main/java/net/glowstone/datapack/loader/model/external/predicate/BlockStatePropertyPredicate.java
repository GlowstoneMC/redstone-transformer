package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;

public class BlockStatePropertyPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:block_state_property";

    private final String block;
    private final Optional<Map<String, String>> properties;

    @JsonCreator
    public BlockStatePropertyPredicate(
        @JsonProperty("block") String block,
        @JsonProperty("properties") Optional<Map<String, String>> properties) {
        this.block = block;
        this.properties = properties;
    }

    public String getBlock() {
        return block;
    }

    public Optional<Map<String, String>> getProperties() {
        return properties;
    }
}
