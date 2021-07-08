package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.DamageType;

public class DamageSourcePropertiesCondition implements Condition {
    public static final String TYPE_ID = "minecraft:damage_source_properties";

    private final DamageType predicate;

    @JsonCreator
    public DamageSourcePropertiesCondition(
        @JsonProperty("predicate") DamageType predicate) {
        this.predicate = predicate;
    }
}
