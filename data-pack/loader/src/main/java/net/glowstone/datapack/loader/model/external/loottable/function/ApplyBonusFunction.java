package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class ApplyBonusFunction extends Function {
    public static class Parameters {
        private final int extra;
        private final float probability;
        private final float bonusMultiplier;

        @JsonCreator
        public Parameters(
            @JsonProperty("extra") int extra,
            @JsonProperty("probability") float probability,
            @JsonProperty("bonusMultiplier") float bonusMultiplier) {
            this.extra = extra;
            this.probability = probability;
            this.bonusMultiplier = bonusMultiplier;
        }

        public int getExtra() {
            return extra;
        }

        public float getProbability() {
            return probability;
        }

        public float getBonusMultiplier() {
            return bonusMultiplier;
        }
    }

    public static final String TYPE_ID = "minecraft:apply_bonus";

    private final String enchantment;
    private final String formula;
    private final Parameters parameters;

    @JsonCreator
    public ApplyBonusFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("enchantment") String enchantment,
        @JsonProperty("formula") String formula,
        @JsonProperty("parameters") Parameters parameters) {
        super(conditions);
        this.enchantment = enchantment;
        this.formula = formula;
        this.parameters = parameters;
    }

    public String getEnchantment() {
        return enchantment;
    }

    public String getFormula() {
        return formula;
    }

    public Parameters getParameters() {
        return parameters;
    }
}
