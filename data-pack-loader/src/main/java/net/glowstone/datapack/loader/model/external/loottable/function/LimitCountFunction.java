package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.util.List;

public class LimitCountFunction extends Function {
    public static final String TYPE_ID = "minecraft:limit_count";

    private final RangedInt limit;

    @JsonCreator
    public LimitCountFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("limit") RangedInt limit) {
        super(conditions);
        this.limit = limit;
    }

    public RangedInt getLimit() {
        return limit;
    }
}
