package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class BrewedPotionConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:brewed_potion";

    private final Optional<String> potion;

    @JsonCreator
    public BrewedPotionConditions(
        @JsonProperty("potion") Optional<String> potion,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.potion = potion;
    }

    public Optional<String> getPotion() {
        return potion;
    }
}
