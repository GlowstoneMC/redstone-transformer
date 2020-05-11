package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RandomChanceCondition implements Condition {
    public static final String TYPE_ID = "minecraft:random_chance";

    private final float chance;

    @JsonCreator
    public RandomChanceCondition(
        @JsonProperty("chance") float chance) {
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }
}
