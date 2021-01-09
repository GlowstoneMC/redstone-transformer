package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Location {
    private final Optional<String> biome;
    private final Optional<LocationBlock> block;
    private final Optional<String> dimension;
    private final Optional<String> feature;
    private final Optional<Fluid> fluid;
    private final Optional<RangedDouble> light;
    private final Optional<Position> position;

    @JsonCreator
    public Location(
        @JsonProperty("biome") Optional<String> biome,
        @JsonProperty("block") Optional<LocationBlock> block,
        @JsonProperty("dimension") Optional<String> dimension,
        @JsonProperty("feature") Optional<String> feature,
        @JsonProperty("fluid") Optional<Fluid> fluid,
        @JsonProperty("light") Optional<RangedDouble> light,
        @JsonProperty("position") Optional<Position> position) {
        this.biome = biome;
        this.block = block;
        this.dimension = dimension;
        this.feature = feature;
        this.fluid = fluid;
        this.light = light;
        this.position = position;
    }

    public Optional<String> getBiome() {
        return biome;
    }

    public Optional<LocationBlock> getBlock() {
        return block;
    }

    public Optional<String> getDimension() {
        return dimension;
    }

    public Optional<String> getFeature() {
        return feature;
    }

    public Optional<Fluid> getFluid() {
        return fluid;
    }

    public Optional<RangedDouble> getLight() {
        return light;
    }

    public Optional<Position> getPosition() {
        return position;
    }
}
