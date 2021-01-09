package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;
import net.glowstone.datapack.loader.model.external.loottable.SourceEntity;

public class EntityPropertiesCondition implements Condition {
    public static final String TYPE_ID = "minecraft:entity_properties";

    private final SourceEntity entity;
    private final Entity predicate;

    @JsonCreator
    public EntityPropertiesCondition(
        @JsonProperty("entity") SourceEntity entity,
        @JsonProperty("predicate") Entity predicate) {
        this.entity = entity;
        this.predicate = predicate;
    }

    public SourceEntity getEntity() {
        return entity;
    }

    public Entity getPredicate() {
        return predicate;
    }
}
