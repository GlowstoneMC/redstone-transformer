package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;

public class EnterBlockConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:enter_block";

    private final Optional<String> block;
    private final Optional<Map<String, String>> state;

    @JsonCreator
    public EnterBlockConditions(
        @JsonProperty("block") Optional<String> block,
        @JsonProperty("state") Optional<Map<String, String>> state) {
        this.block = block;
        this.state = state;
    }

    public Optional<String> getBlock() {
        return block;
    }

    public Optional<Map<String, String>> getState() {
        return state;
    }
}
