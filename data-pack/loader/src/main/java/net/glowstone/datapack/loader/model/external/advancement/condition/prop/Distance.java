package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Distance {
    private final Optional<RangedDouble> absolute;
    private final Optional<RangedDouble> horizontal;
    private final Optional<RangedDouble> x;
    private final Optional<RangedDouble> y;
    private final Optional<RangedDouble> z;

    @JsonCreator
    public Distance(
        @JsonProperty("absolute") Optional<RangedDouble> absolute,
        @JsonProperty("horizontal") Optional<RangedDouble> horizontal,
        @JsonProperty("x") Optional<RangedDouble> x,
        @JsonProperty("y") Optional<RangedDouble> y,
        @JsonProperty("z") Optional<RangedDouble> z) {
        this.absolute = absolute;
        this.horizontal = horizontal;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Optional<RangedDouble> getAbsolute() {
        return absolute;
    }

    public Optional<RangedDouble> getHorizontal() {
        return horizontal;
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
