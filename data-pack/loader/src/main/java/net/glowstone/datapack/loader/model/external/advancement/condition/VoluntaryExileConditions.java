package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Fluid;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Location;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.LocationBlock;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Position;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedDouble;

import java.util.Optional;

public class VoluntaryExileConditions extends Location implements Conditions {
    public static final String TYPE_ID = "minecraft:voluntary_exile";

    private final Optional<Location> location;

    @JsonCreator
    public VoluntaryExileConditions(
        @JsonProperty("biome") Optional<String> biome,
        @JsonProperty("block") Optional<LocationBlock> block,
        @JsonProperty("dimension") Optional<String> dimension,
        @JsonProperty("feature") Optional<String> feature,
        @JsonProperty("fluid") Optional<Fluid> fluid,
        @JsonProperty("light") Optional<RangedDouble> light,
        @JsonProperty("position") Optional<Position> position,
        @JsonProperty("location") Optional<Location> location) {
        super(biome, block, dimension, feature, fluid, light, position);

        this.location = location;
    }

    public Optional<Location> getLocation() {
        return location;
    }
}
