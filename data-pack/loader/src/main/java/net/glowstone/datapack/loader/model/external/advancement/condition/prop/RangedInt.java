package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.math.DoubleMath;

import java.util.OptionalInt;

import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsInt;

public class RangedInt {
    @JsonCreator
    public static RangedInt fromJson(JsonNode rootNode) throws JsonMappingException {
        if (rootNode.isInt()) {
            OptionalInt value = OptionalInt.of(rootNode.asInt());
            return new RangedInt(value, value);
        } else if (rootNode.isDouble()) {
            double value = rootNode.asDouble();
            if (DoubleMath.isMathematicalInteger(value)) {
                OptionalInt intValue = OptionalInt.of((int)value);
                return new RangedInt(intValue, intValue);
            }
        } else if (rootNode.isObject()) {
            return new RangedInt(valueAsInt(rootNode, "min"), valueAsInt(rootNode, "max"));
        }
        throw new JsonMappingException(null, "Cannot create RangedInt.");
    }

    private final OptionalInt minValue;
    private final OptionalInt maxValue;

    public RangedInt(OptionalInt minValue, OptionalInt maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public OptionalInt getMinValue() {
        return minValue;
    }

    public OptionalInt getMaxValue() {
        return maxValue;
    }
}
