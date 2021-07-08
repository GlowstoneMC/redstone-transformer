package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TableBonusCondition implements Condition {
    public static final String TYPE_ID = "minecraft:table_bonus";

    private final String enchantment;
    private final List<Float> chances;

    @JsonCreator
    public TableBonusCondition(
        @JsonProperty("enchantment") String enchantment,
        @JsonProperty("chances") List<Float> chances) {
        this.enchantment = enchantment;
        this.chances = chances;
    }

    public String getEnchantment() {
        return enchantment;
    }

    public List<Float> getChances() {
        return chances;
    }
}
