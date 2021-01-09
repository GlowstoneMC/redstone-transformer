package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;

public class Fluid {
    private final Optional<String> fluid;
    private final Optional<String> tag;
    private final Map<String, PropertyValue<?>> state;

    @JsonCreator
    public Fluid(
        @JsonProperty("fluid") Optional<String> fluid,
        @JsonProperty("tag") Optional<String> tag,
        @JsonProperty("state") Map<String, PropertyValue<?>> state) {
        this.fluid = fluid;
        this.tag = tag;
        this.state = state;
    }

    public Optional<String> getFluid() {
        return fluid;
    }

    public Optional<String> getTag() {
        return tag;
    }

    public Map<String, PropertyValue<?>> getState() {
        return state;
    }
}
