package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class ConstructBeaconConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:construct_beacon";

    private final Optional<RangedInt> level;

    @JsonCreator
    public ConstructBeaconConditions(
        @JsonProperty("level") Optional<RangedInt> level,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.level = level;
    }

    public Optional<RangedInt> getLevel() {
        return level;
    }
}
