package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Distance;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class LevitationConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:levitation";

    private final Optional<Distance> distance;
    private final Optional<RangedInt> duration;

    @JsonCreator
    public LevitationConditions(
        @JsonProperty("distance") Optional<Distance> distance,
        @JsonProperty("duration") Optional<RangedInt> duration,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
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
