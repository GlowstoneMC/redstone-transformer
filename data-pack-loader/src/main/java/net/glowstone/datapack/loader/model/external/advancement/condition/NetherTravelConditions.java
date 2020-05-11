package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Distance;

import java.util.Optional;

public class NetherTravelConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:nether_travel";

    private final Optional<Distance> distance;

    @JsonCreator
    public NetherTravelConditions(
        @JsonProperty("distance") Optional<Distance> distance) {
        this.distance = distance;
    }

    public Optional<Distance> getDistance() {
        return distance;
    }
}
