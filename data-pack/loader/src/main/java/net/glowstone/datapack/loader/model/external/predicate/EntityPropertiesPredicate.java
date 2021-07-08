package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;

public class EntityPropertiesPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:entity_properties";

    private final String entity;
    private final Entity predicate;

    @JsonCreator
    public EntityPropertiesPredicate(
        @JsonProperty("entity") String entity,
        @JsonProperty("predicate") Entity predicate) {
        this.entity = entity;
        this.predicate = predicate;
    }

    public String getEntity() {
        return entity;
    }

    public Entity getPredicate() {
        return predicate;
    }
}
