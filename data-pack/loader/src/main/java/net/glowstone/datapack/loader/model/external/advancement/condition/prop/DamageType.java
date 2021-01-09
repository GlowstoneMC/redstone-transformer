package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class DamageType {
    private final Optional<Boolean> bypassesArmor;
    private final Optional<Boolean> bypassesInvulnerability;
    private final Optional<Boolean> bypassesMagic;
    private final Optional<Entity> directEntity;
    private final Optional<Boolean> isExplosion;
    private final Optional<Boolean> isFire;
    private final Optional<Boolean> isMagic;
    private final Optional<Boolean> isProjectile;
    private final Optional<Boolean> isLightning;
    private final Optional<Entity> sourceEntity;

    @JsonCreator
    public DamageType(
        @JsonProperty("bypasses_armor") Optional<Boolean> bypassesArmor,
        @JsonProperty("bypasses_invulnerability") Optional<Boolean> bypassesInvulnerability,
        @JsonProperty("bypasses_magic") Optional<Boolean> bypassesMagic,
        @JsonProperty("direct_entity") Optional<Entity> directEntity,
        @JsonProperty("is_explosion") Optional<Boolean> isExplosion,
        @JsonProperty("is_fire") Optional<Boolean> isFire,
        @JsonProperty("is_magic") Optional<Boolean> isMagic,
        @JsonProperty("is_projectile") Optional<Boolean> isProjectile,
        @JsonProperty("is_lightning") Optional<Boolean> isLightning,
        @JsonProperty("source_entry") Optional<Entity> sourceEntity) {
        this.bypassesArmor = bypassesArmor;
        this.bypassesInvulnerability = bypassesInvulnerability;
        this.bypassesMagic = bypassesMagic;
        this.directEntity = directEntity;
        this.isExplosion = isExplosion;
        this.isFire = isFire;
        this.isMagic = isMagic;
        this.isProjectile = isProjectile;
        this.isLightning = isLightning;
        this.sourceEntity = sourceEntity;
    }

    public Optional<Boolean> getBypassesArmor() {
        return bypassesArmor;
    }

    public Optional<Boolean> getBypassesInvulnerability() {
        return bypassesInvulnerability;
    }

    public Optional<Boolean> getBypassesMagic() {
        return bypassesMagic;
    }

    public Optional<Entity> getDirectEntity() {
        return directEntity;
    }

    public Optional<Boolean> getIsExplosion() {
        return isExplosion;
    }

    public Optional<Boolean> getIsFire() {
        return isFire;
    }

    public Optional<Boolean> getIsMagic() {
        return isMagic;
    }

    public Optional<Boolean> getIsProjectile() {
        return isProjectile;
    }

    public Optional<Boolean> getIsLightning() {
        return isLightning;
    }

    public Optional<Entity> getSourceEntity() {
        return sourceEntity;
    }
}
