package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class CopyStateFunction extends Function {
    public static final String TYPE_ID = "minecraft:copy_state";

    private final String block;
    private final List<String> properties;

    @JsonCreator
    public CopyStateFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("block") String block,
        @JsonProperty("properties") List<String> properties) {
        super(conditions);
        this.block = block;
        this.properties = properties;
    }

    public String getBlock() {
        return block;
    }

    public List<String> getProperties() {
        return properties;
    }
}
