package net.glowstone.datapack.loader.model.external.loottable.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class EmptyPoolEntry extends PoolEntry {
    public static final String TYPE_ID = "minecraft:empty";

    @JsonCreator
    public EmptyPoolEntry(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("weight") int weight,
        @JsonProperty("quality") int quality) {
        super(conditions, weight, quality);
    }
}
