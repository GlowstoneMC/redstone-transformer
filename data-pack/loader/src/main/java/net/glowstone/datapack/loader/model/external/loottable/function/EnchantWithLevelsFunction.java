package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.util.List;

public class EnchantWithLevelsFunction extends Function {
    public static final String TYPE_ID = "minecraft:enchant_with_levels";

    private final boolean treasure;
    private final RangedInt levels;

    @JsonCreator
    public EnchantWithLevelsFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("treasure") boolean treasure,
        @JsonProperty("levels") RangedInt levels) {
        super(conditions);
        this.treasure = treasure;
        this.levels = levels;
    }

    public boolean isTreasure() {
        return treasure;
    }

    public RangedInt getLevels() {
        return levels;
    }
}
