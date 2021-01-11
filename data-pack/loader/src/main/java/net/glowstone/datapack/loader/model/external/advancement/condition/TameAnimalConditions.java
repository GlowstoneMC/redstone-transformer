package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class TameAnimalConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:tame_animal";

    private final Optional<List<Predicate>> entity;

    @JsonCreator
    public TameAnimalConditions(
        @JsonProperty("entity") Optional<List<Predicate>> entity,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.entity = entity;
    }

    public Optional<List<Predicate>> getEntity() {
        return entity;
    }
}
