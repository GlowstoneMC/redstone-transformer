package net.glowstone.datapack.loader.model.external.loottable.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class LootTablePoolEntry extends PoolEntry {
    public static final String TYPE_ID = "minecraft:loot_table";

    private final String name;

    @JsonCreator
    public LootTablePoolEntry(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("weight") int weight,
        @JsonProperty("quality") int quality,
        @JsonProperty("name") String name) {
        super(conditions, weight, quality);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
