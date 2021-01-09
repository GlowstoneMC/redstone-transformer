package net.glowstone.datapack.loader.model.external.loottable.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class TagPoolEntry extends PoolEntry {
    public static final String TYPE_ID = "minecraft:tag";

    private final String name;
    private final boolean expand;

    @JsonCreator
    public TagPoolEntry(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("weight") int weight,
        @JsonProperty("quality") int quality,
        @JsonProperty("name") String name,
        @JsonProperty("expand") boolean expand) {
        super(conditions, weight, quality);
        this.name = name;
        this.expand = expand;
    }

    public String getName() {
        return name;
    }

    public boolean isExpand() {
        return expand;
    }
}
