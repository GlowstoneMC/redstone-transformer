package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedDoubleInt;

import java.util.Optional;

public class UsedEnderEyeConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:used_ender_eye";

    private final Optional<RangedDoubleInt> distance;

    @JsonCreator
    public UsedEnderEyeConditions(
        @JsonProperty("distance") Optional<RangedDoubleInt> distance) {
        this.distance = distance;
    }

    public Optional<RangedDoubleInt> getDistance() {
        return distance;
    }
}
