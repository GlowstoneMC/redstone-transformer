package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Damage;

import java.util.Optional;

public class EntityHurtPlayerConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:entity_hurt_player";
    
    private final Optional<Damage> damage;

    @JsonCreator
    public EntityHurtPlayerConditions(
        @JsonProperty("damage") Optional<Damage> damage) {
        this.damage = damage;
    }

    public Optional<Damage> getDamage() {
        return damage;
    }
}
