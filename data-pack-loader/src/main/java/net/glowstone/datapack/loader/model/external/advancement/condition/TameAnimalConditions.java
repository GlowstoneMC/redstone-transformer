package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;

import java.util.Optional;

public class TameAnimalConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:tame_animal";

    private final Optional<Entity> entity;

    @JsonCreator
    public TameAnimalConditions(
        @JsonProperty("entity") Optional<Entity> entity) {
        this.entity = entity;
    }

    public Optional<Entity> getEntity() {
        return entity;
    }
}
