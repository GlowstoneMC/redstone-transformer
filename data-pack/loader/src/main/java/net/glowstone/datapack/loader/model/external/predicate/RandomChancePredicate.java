package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RandomChancePredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:random_chance";

    private final float chance;

    @JsonCreator
    public RandomChancePredicate(
        @JsonProperty("chance") float chance) {
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }
}
