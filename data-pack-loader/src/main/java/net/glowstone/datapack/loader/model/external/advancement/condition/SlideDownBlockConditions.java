package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class SlideDownBlockConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:slide_down_block";

    private final Optional<String> block;

    @JsonCreator
    public SlideDownBlockConditions(
        @JsonProperty("block") Optional<String> block) {
        this.block = block;
    }

    public Optional<String> getBlock() {
        return block;
    }
}
