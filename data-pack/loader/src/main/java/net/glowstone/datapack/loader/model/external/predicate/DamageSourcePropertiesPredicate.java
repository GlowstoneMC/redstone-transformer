package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.DamageType;

public class DamageSourcePropertiesPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:damage_source_properties";
    
    private final DamageType predicate;

    @JsonCreator
    public DamageSourcePropertiesPredicate(
        @JsonProperty("predicate") DamageType predicate) {
        this.predicate = predicate;
    }

    public DamageType getPredicate() {
        return predicate;
    }
}
