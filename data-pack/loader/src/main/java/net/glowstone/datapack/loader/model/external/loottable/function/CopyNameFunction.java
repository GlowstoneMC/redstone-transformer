package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class CopyNameFunction extends Function {
    public enum Source {
        @JsonProperty("block_entity") BLOCK_ENTITY,
    }

    public static final String TYPE_ID = "minecraft:copy_name";

    private final Source source;

    @JsonCreator
    public CopyNameFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("source") Source source) {
        super(conditions);
        this.source = source;
    }

    public Source getSource() {
        return source;
    }
}
