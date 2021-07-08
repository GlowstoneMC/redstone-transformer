package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class CuredZombieVillagerConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:cured_zombie_villager";

    private final Optional<List<Predicate>> villager;
    private final Optional<List<Predicate>> zombie;

    @JsonCreator
    public CuredZombieVillagerConditions(
        @JsonProperty("villager") Optional<List<Predicate>> villager,
        @JsonProperty("zombie") Optional<List<Predicate>> zombie,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.villager = villager;
        this.zombie = zombie;
    }

    public Optional<List<Predicate>> getVillager() {
        return villager;
    }

    public Optional<List<Predicate>> getZombie() {
        return zombie;
    }
}
