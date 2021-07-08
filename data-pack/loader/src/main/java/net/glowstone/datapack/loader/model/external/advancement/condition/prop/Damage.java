package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Damage {
    private final Optional<Boolean> blocked;
    private final Optional<RangedDouble> dealt;
    private final Optional<Entity> sourceEntity;
    private final Optional<RangedDouble> taken;
    private final Optional<DamageType> type;

    @JsonCreator
    public Damage(
        @JsonProperty("blocked") Optional<Boolean> blocked,
        @JsonProperty("dealt") Optional<RangedDouble> dealt,
        @JsonProperty("source_entity") Optional<Entity> sourceEntity,
        @JsonProperty("taken") Optional<RangedDouble> taken,
        @JsonProperty("type") Optional<DamageType> type) {
        this.blocked = blocked;
        this.dealt = dealt;
        this.sourceEntity = sourceEntity;
        this.taken = taken;
        this.type = type;
    }

    public Optional<Boolean> getBlocked() {
        return blocked;
    }

    public Optional<RangedDouble> getDealt() {
        return dealt;
    }

    public Optional<Entity> getSourceEntity() {
        return sourceEntity;
    }

    public Optional<RangedDouble> getTaken() {
        return taken;
    }

    public Optional<DamageType> getType() {
        return type;
    }
}
