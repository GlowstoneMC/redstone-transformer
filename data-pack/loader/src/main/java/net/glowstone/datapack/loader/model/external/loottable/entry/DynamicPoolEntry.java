package net.glowstone.datapack.loader.model.external.loottable.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class DynamicPoolEntry extends PoolEntry {
    public enum Name {
        @JsonProperty("minecraft:contents") CONTENTS,
        @JsonProperty("minecraft:self") SELF,
    }

    public static final String TYPE_ID = "minecraft:dynamic";

    private final Name name;

    @JsonCreator
    public DynamicPoolEntry(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("weight") int weight,
        @JsonProperty("quality") int quality,
        @JsonProperty("name") Name name) {
        super(conditions, weight, quality);
        this.name = name;
    }

    public Name getName() {
        return name;
    }
}
