package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Damage;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;

import java.util.Optional;

public class PlayerHurtEntityConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:player_hurt_entity";

    private final Optional<Damage> damage;
    private final Optional<Entity> entity;

    @JsonCreator
    public PlayerHurtEntityConditions(
        @JsonProperty("damage") Optional<Damage> damage,
        @JsonProperty("entity") Optional<Entity> entity) {
        this.damage = damage;
        this.entity = entity;
    }

    public Optional<Damage> getDamage() {
        return damage;
    }

    public Optional<Entity> getEntity() {
        return entity;
    }
}
