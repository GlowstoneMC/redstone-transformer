package net.glowstone.datapack.loader.model.external.loottable.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.function.Function;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class ItemPoolEntry extends PoolEntry {
    public static final String TYPE_ID = "minecraft:item";
    
    private final String name;
    private final List<Function> functions;

    @JsonCreator
    public ItemPoolEntry(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("weight") int weight,
        @JsonProperty("quality") int quality,
        @JsonProperty("name") String name,
        @JsonProperty("functions") List<Function> functions) {
        super(conditions, weight, quality);
        this.name = name;
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public List<Function> getFunctions() {
        return functions;
    }
}
