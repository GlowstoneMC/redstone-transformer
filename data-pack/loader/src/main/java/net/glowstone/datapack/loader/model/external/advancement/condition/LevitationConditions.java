package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Distance;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.util.Optional;

public class LevitationConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:levitation";

    private final Optional<Distance> distance;
    private final Optional<RangedInt> duration;

    @JsonCreator
    public LevitationConditions(
        @JsonProperty("distance") Optional<Distance> distance,
        @JsonProperty("duration") Optional<RangedInt> duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public Optional<Distance> getDistance() {
        return distance;
    }

    public Optional<RangedInt> getDuration() {
        return duration;
    }
}
