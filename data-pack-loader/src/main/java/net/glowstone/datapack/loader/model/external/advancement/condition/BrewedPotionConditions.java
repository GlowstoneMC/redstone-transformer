package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class BrewedPotionConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:brewed_potion";

    private final Optional<String> potion;

    @JsonCreator
    public BrewedPotionConditions(
        @JsonProperty("potion") Optional<String> potion) {
        this.potion = potion;
    }

    public Optional<String> getPotion() {
        return potion;
    }
}
