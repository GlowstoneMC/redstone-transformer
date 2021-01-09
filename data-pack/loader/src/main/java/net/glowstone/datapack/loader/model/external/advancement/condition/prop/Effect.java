package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Effect {
    private final Optional<RangedInt> amplifier;
    private final Optional<RangedInt> duration;

    @JsonCreator
    public Effect(
        @JsonProperty("amplifier") Optional<RangedInt> amplifier,
        @JsonProperty("duration") Optional<RangedInt> duration) {
        this.amplifier = amplifier;
        this.duration = duration;
    }

    public Optional<RangedInt> getAmplifier() {
        return amplifier;
    }

    public Optional<RangedInt> getDuration() {
        return duration;
    }
}
