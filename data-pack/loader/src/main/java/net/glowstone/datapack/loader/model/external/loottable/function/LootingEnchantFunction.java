package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.util.List;

public class LootingEnchantFunction extends Function {
    public static final String TYPE_ID = "minecraft:looting_enchant";

    private final RangedInt count;
    private final int limit;

    @JsonCreator
    public LootingEnchantFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("count") RangedInt count,
        @JsonProperty("limit") int limit) {
        super(conditions);
        this.count = count;
        this.limit = limit;
    }

    public RangedInt getCount() {
        return count;
    }

    public int getLimit() {
        return limit;
    }
}
