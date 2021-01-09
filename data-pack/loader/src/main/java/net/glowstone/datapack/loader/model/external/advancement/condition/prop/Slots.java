package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Slots {
    private final Optional<RangedInt> empty;
    private final Optional<RangedInt> full;
    private final Optional<RangedInt> occupied;

    @JsonCreator
    public Slots(
        @JsonProperty("empty") Optional<RangedInt> empty,
        @JsonProperty("full") Optional<RangedInt> full,
        @JsonProperty("occupied") Optional<RangedInt> occupied) {
        this.empty = empty;
        this.full = full;
        this.occupied = occupied;
    }

    public Optional<RangedInt> getEmpty() {
        return empty;
    }

    public Optional<RangedInt> getFull() {
        return full;
    }

    public Optional<RangedInt> getOccupied() {
        return occupied;
    }
}
