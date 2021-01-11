package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class TargetHitConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:target_hit";

    private final OptionalInt signalStrength;
    private final Optional<List<Predicate>> projectile;

    @JsonCreator
    public TargetHitConditions(
        @JsonProperty("signal_strength") OptionalInt signalStrength,
        @JsonProperty("projectile") Optional<List<Predicate>> projectile,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.signalStrength = signalStrength;
        this.projectile = projectile;
    }

    public OptionalInt getSignalStrength() {
        return signalStrength;
    }

    public Optional<List<Predicate>> getProjectile() {
        return projectile;
    }
}
