package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class FurnaceSmeltFunction extends Function {
    public static final String TYPE_ID = "minecraft:furnace_smelt";

    @JsonCreator
    public FurnaceSmeltFunction(
        @JsonProperty("conditions") List<Condition> conditions) {
        super(conditions);
    }
}
