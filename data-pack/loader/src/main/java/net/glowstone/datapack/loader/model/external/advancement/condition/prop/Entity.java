package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;

public class Entity {
    public static class Flags {
        private final Optional<Boolean> onFire;
        private final Optional<Boolean> sneaking;
        private final Optional<Boolean> sprinting;
        private final Optional<Boolean> swimming;
        private final Optional<Boolean> baby;

        @JsonCreator
        public Flags(
            @JsonProperty("is_on_fire") Optional<Boolean> onFire,
            @JsonProperty("is_sneaking") Optional<Boolean> sneaking,
            @JsonProperty("is_sprinting") Optional<Boolean> sprinting,
            @JsonProperty("is_swimming") Optional<Boolean> swimming,
            @JsonProperty("is_baby") Optional<Boolean> baby) {
            this.onFire = onFire;
            this.sneaking = sneaking;
            this.sprinting = sprinting;
            this.swimming = swimming;
            this.baby = baby;
        }

        public Optional<Boolean> getOnFire() {
            return onFire;
        }

        public Optional<Boolean> getSneaking() {
            return sneaking;
        }

        public Optional<Boolean> getSprinting() {
            return sprinting;
        }

        public Optional<Boolean> getSwimming() {
            return swimming;
        }

        public Optional<Boolean> getBaby() {
            return baby;
        }
    }

    private final Optional<String> catType;
    private final Optional<Distance> distance;
    private final Optional<Map<String, StatusEffect>> effects;
    private final Optional<Equipment> equipment;
    private final Optional<Flags> flags;
    private final Optional<Location> location;
    private final Optional<String> nbt;
    private final Optional<Player> player;
    private final Optional<String> team;
    private final Optional<String> type;

    @JsonCreator
    public Entity(
        @JsonProperty("catType") Optional<String> catType,
        @JsonProperty("distance") Optional<Distance> distance,
        @JsonProperty("effects") Optional<Map<String, StatusEffect>> effects,
        @JsonProperty("equipment") Optional<Equipment> equipment,
        @JsonProperty("flags") Optional<Flags> flags,
        @JsonProperty("location") Optional<Location> location,
        @JsonProperty("nbt") Optional<String> nbt,
        @JsonProperty("player") Optional<Player> player,
        @JsonProperty("team") Optional<String> team,
        @JsonProperty("type") Optional<String> type) {
        this.catType = catType;
        this.distance = distance;
        this.effects = effects;
        this.equipment = equipment;
        this.flags = flags;
        this.location = location;
        this.nbt = nbt;
        this.player = player;
        this.team = team;
        this.type = type;
    }

    public Optional<String> getCatType() {
        return catType;
    }

    public Optional<Distance> getDistance() {
        return distance;
    }

    public Optional<Map<String, StatusEffect>> getEffects() {
        return effects;
    }

    public Optional<Equipment> getEquipment() {
        return equipment;
    }

    public Optional<Flags> getFlags() {
        return flags;
    }

    public Optional<Location> getLocation() {
        return location;
    }

    public Optional<String> getNbt() {
        return nbt;
    }

    public Optional<Player> getPlayer() {
        return player;
    }

    public Optional<String> getTeam() {
        return team;
    }

    public Optional<String> getType() {
        return type;
    }
}
