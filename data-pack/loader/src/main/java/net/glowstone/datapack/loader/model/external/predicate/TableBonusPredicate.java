package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TableBonusPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:table_bonus";

    private final int enchantment;
    private final List<Float> chances;

    @JsonCreator
    public TableBonusPredicate(
        @JsonProperty("enchantment") int enchantment,
        @JsonProperty("chances") List<Float> chances) {
        this.enchantment = enchantment;
        this.chances = chances;
    }

    public int getEnchantment() {
        return enchantment;
    }

    public List<Float> getChances() {
        return chances;
    }
}
