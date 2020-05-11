package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;

public class LocationBlock extends Block {
    private final Optional<String> nbt;
    private final Map<String, PropertyValue<?>> state;

    @JsonCreator
    public LocationBlock(
        @JsonProperty("block") Optional<String> block,
        @JsonProperty("tag") Optional<String> tag,
        @JsonProperty("nbt") Optional<String> nbt,
        @JsonProperty("state") Map<String, PropertyValue<?>> state) {
        super(block, tag);
        this.nbt = nbt;
        this.state = state;
    }

    public Optional<String> getNbt() {
        return nbt;
    }

    public Map<String, PropertyValue<?>> getState() {
        return state;
    }
}
