package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;

public class Block {
    private final Optional<String> block;
    private final Optional<String> tag;

    @JsonCreator
    public Block(
        @JsonProperty("block") Optional<String> block,
        @JsonProperty("tag") Optional<String> tag) {
        this.block = block;
        this.tag = tag;
    }

    public Optional<String> getBlock() {
        return block;
    }

    public Optional<String> getTag() {
        return tag;
    }
}
