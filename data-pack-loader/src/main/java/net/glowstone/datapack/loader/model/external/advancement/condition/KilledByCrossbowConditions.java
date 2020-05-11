package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.util.List;
import java.util.Optional;

public class KilledByCrossbowConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:killed_by_crossbow";

    private final Optional<RangedInt> uniqueEntityTypes;
    private final Optional<List<Entity>> victims;

    @JsonCreator
    public KilledByCrossbowConditions(
        @JsonProperty("unique_entity_types") Optional<RangedInt> uniqueEntityTypes,
        @JsonProperty("victims") Optional<List<Entity>> victims) {
        this.uniqueEntityTypes = uniqueEntityTypes;
        this.victims = victims;
    }

    public Optional<RangedInt> getUniqueEntityTypes() {
        return uniqueEntityTypes;
    }

    public Optional<List<Entity>> getVictims() {
        return victims;
    }
}
