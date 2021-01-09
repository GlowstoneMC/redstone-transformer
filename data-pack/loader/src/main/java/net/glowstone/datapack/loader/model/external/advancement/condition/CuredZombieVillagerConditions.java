package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;

import java.util.Optional;

public class CuredZombieVillagerConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:cured_zombie_villager";

    private final Optional<Entity> villager;
    private final Optional<Entity> zombie;

    @JsonCreator
    public CuredZombieVillagerConditions(
        @JsonProperty("villager") Optional<Entity> villager,
        @JsonProperty("zombie") Optional<Entity> zombie) {
        this.villager = villager;
        this.zombie = zombie;
    }

    public Optional<Entity> getVillager() {
        return villager;
    }

    public Optional<Entity> getZombie() {
        return zombie;
    }
}
