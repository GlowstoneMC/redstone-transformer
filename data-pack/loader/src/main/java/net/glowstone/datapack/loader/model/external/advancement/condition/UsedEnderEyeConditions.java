package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedDoubleInt;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class UsedEnderEyeConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:used_ender_eye";

    private final Optional<RangedDoubleInt> distance;

    @JsonCreator
    public UsedEnderEyeConditions(
        @JsonProperty("distance") Optional<RangedDoubleInt> distance,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.distance = distance;
    }

    public Optional<RangedDoubleInt> getDistance() {
        return distance;
    }
}
