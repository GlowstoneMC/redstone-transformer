package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.util.Optional;

public class ConstructBeaconConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:construct_beacon";

    private final Optional<RangedInt> level;

    @JsonCreator
    public ConstructBeaconConditions(
        @JsonProperty("level") Optional<RangedInt> level) {
        this.level = level;
    }

    public Optional<RangedInt> getLevel() {
        return level;
    }
}
