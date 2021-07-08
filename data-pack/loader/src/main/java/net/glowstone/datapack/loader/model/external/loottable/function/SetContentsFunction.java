package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.loottable.entry.PoolEntry;

import java.util.List;

public class SetContentsFunction extends Function {
    public static final String TYPE_ID = "minecraft:set_contents";

    private final List<PoolEntry> entries;

    @JsonCreator
    public SetContentsFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("entries") List<PoolEntry> entries) {
        super(conditions);
        this.entries = entries;
    }

    public List<PoolEntry> getEntries() {
        return entries;
    }
}
