package net.glowstone.datapack.loader.model.external.loottable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.function.Function;

import java.util.List;
import java.util.Optional;

public class LootTable {
    public enum Type {
        @JsonProperty("minecraft:empty") EMPTY,
        @JsonProperty("minecraft:entity") ENTITY,
        @JsonProperty("minecraft:block") BLOCK,
        @JsonProperty("minecraft:chest") CHEST,
        @JsonProperty("minecraft:fishing") FISHING,
        @JsonProperty("minecraft:gift") GIFT,
        @JsonProperty("minecraft:advancement_reward") ADVANCEMENT_REWARD,
        @JsonProperty("minecraft:barter") BARTER,
        @JsonProperty("minecraft:generic") GENERIC,
        }

    private final Optional<Type> type;
    private final List<Pool> pools;
    private final List<Function> functions;

    @JsonCreator
    public LootTable(
        @JsonProperty("type") Optional<Type> type,
        @JsonProperty("pools") List<Pool> pools,
        @JsonProperty("functions") List<Function> functions) {
        this.type = type;
        this.pools = pools;
        this.functions = functions;
    }

    public Optional<Type> getType() {
        return type;
    }

    public List<Pool> getPools() {
        return pools;
    }

    public List<Function> getFunctions() {
        return functions;
    }
}
