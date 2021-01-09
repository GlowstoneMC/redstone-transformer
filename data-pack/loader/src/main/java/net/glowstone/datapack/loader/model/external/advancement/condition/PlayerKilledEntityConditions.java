package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.DamageType;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;

import java.util.Optional;

public class PlayerKilledEntityConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:player_killed_entity";

    private final Optional<Entity> entity;
    private final Optional<DamageType> killingBlow;

    @JsonCreator
    public PlayerKilledEntityConditions(
        @JsonProperty("entity") Optional<Entity> entity,
        @JsonProperty("killing_blow") Optional<DamageType> killingBlow) {
        this.entity = entity;
        this.killingBlow = killingBlow;
    }

    public Optional<Entity> getEntity() {
        return entity;
    }

    public Optional<DamageType> getKillingBlow() {
        return killingBlow;
    }
}
