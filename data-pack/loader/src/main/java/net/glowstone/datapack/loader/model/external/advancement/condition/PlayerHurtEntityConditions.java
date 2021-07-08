package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Damage;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class PlayerHurtEntityConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:player_hurt_entity";

    private final Optional<Damage> damage;
    private final Optional<List<Predicate>> entity;

    @JsonCreator
    public PlayerHurtEntityConditions(
        @JsonProperty("damage") Optional<Damage> damage,
        @JsonProperty("entity") Optional<List<Predicate>> entity,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.damage = damage;
        this.entity = entity;
    }

    public Optional<Damage> getDamage() {
        return damage;
    }

    public Optional<List<Predicate>> getEntity() {
        return entity;
    }
}
