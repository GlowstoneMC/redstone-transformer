package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;

import java.util.Optional;

public class SummonedEntityConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:summoned_entity";

    private final Optional<Entity> entity;

    @JsonCreator
    public SummonedEntityConditions(
        @JsonProperty("entity") Optional<Entity> entity) {
        this.entity = entity;
    }

    public Optional<Entity> getEntity() {
        return entity;
    }
}
