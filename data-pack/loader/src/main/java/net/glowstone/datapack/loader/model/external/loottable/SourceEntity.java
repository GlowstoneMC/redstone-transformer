package net.glowstone.datapack.loader.model.external.loottable;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SourceEntity {
    @JsonProperty("block_entity") BLOCK_ENTITY,
    @JsonProperty("this") THIS,
    @JsonProperty("killer") KILLER,
    @JsonProperty("killer_player") KILLER_PLAYER,
}
