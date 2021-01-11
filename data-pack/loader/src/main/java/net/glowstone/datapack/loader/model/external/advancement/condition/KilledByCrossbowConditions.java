package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class KilledByCrossbowConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:killed_by_crossbow";

    private final Optional<RangedInt> uniqueEntityTypes;
    private final Optional<List<List<Predicate>>> victims;

    @JsonCreator
    public KilledByCrossbowConditions(
        @JsonProperty("unique_entity_types") Optional<RangedInt> uniqueEntityTypes,
        @JsonProperty("victims") Optional<List<List<Predicate>>> victims,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.uniqueEntityTypes = uniqueEntityTypes;
        this.victims = victims;
    }

    public Optional<RangedInt> getUniqueEntityTypes() {
        return uniqueEntityTypes;
    }

    public Optional<List<List<Predicate>>> getVictims() {
        return victims;
    }
}
