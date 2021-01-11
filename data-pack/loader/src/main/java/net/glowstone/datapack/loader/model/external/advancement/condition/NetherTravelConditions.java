package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Distance;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class NetherTravelConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:nether_travel";

    private final Optional<Distance> distance;

    @JsonCreator
    public NetherTravelConditions(
        @JsonProperty("distance") Optional<Distance> distance,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.distance = distance;
    }

    public Optional<Distance> getDistance() {
        return distance;
    }
}
