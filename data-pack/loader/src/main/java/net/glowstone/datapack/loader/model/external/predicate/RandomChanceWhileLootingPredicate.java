package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RandomChanceWhileLootingPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:random_chance_with_looting";

    private final float chance;
    private final float lootingMultiplier;

    @JsonCreator
    public RandomChanceWhileLootingPredicate(
        @JsonProperty("chance") float chance,
        @JsonProperty("looting_multiplier") float lootingMultiplier) {
        this.chance = chance;
        this.lootingMultiplier = lootingMultiplier;
    }

    public float getChance() {
        return chance;
    }

    public float getLootingMultiplier() {
        return lootingMultiplier;
    }
}
