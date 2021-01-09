package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class SetNbtFunction extends Function {
    public static final String TYPE_ID = "minecraft:set_nbt";

    private final String tag;

    @JsonCreator
    public SetNbtFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("tag") String tag) {
        super(conditions);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
