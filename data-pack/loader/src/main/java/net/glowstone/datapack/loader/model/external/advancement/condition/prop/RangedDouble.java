package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.OptionalDouble;
import java.util.OptionalInt;

import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsDouble;

public class RangedDouble {
    @JsonCreator
    public static RangedDouble fromJson(JsonNode rootNode) throws JsonMappingException {
        if (rootNode.isDouble()) {
            OptionalDouble value = OptionalDouble.of(rootNode.asDouble());
            return new RangedDouble(value, value);
        } else if (rootNode.isObject()) {
            return new RangedDouble(valueAsDouble(rootNode, "min"), valueAsDouble(rootNode, "max"));
        }
        throw new JsonMappingException(null, "Cannot create RangedDouble.");
    }

    private final OptionalDouble minValue;
    private final OptionalDouble maxValue;

    public RangedDouble(OptionalDouble minValue, OptionalDouble maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public OptionalDouble getMinValue() {
        return minValue;
    }

    public OptionalDouble getMaxValue() {
        return maxValue;
    }
}
