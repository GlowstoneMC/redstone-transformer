package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Position {
    private final Optional<RangedDouble> x;
    private final Optional<RangedDouble> y;
    private final Optional<RangedDouble> z;

    @JsonCreator
    public Position(
        @JsonProperty("x") Optional<RangedDouble> x,
        @JsonProperty("y") Optional<RangedDouble> y,
        @JsonProperty("z") Optional<RangedDouble> z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Optional<RangedDouble> getX() {
        return x;
    }

    public Optional<RangedDouble> getY() {
        return y;
    }

    public Optional<RangedDouble> getZ() {
        return z;
    }
}
