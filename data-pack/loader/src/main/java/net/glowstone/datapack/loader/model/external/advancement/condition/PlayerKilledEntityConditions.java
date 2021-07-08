package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.DamageType;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class PlayerKilledEntityConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:player_killed_entity";

    private final Optional<List<Predicate>> entity;
    private final Optional<DamageType> killingBlow;

    @JsonCreator
    public PlayerKilledEntityConditions(
        @JsonProperty("entity") Optional<List<Predicate>> entity,
        @JsonProperty("killing_blow") Optional<DamageType> killingBlow,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.entity = entity;
        this.killingBlow = killingBlow;
    }

    public Optional<List<Predicate>> getEntity() {
        return entity;
    }

    public Optional<DamageType> getKillingBlow() {
        return killingBlow;
    }
}
