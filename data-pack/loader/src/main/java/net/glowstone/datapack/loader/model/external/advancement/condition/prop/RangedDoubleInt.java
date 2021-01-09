package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsDouble;

public class RangedDoubleInt {
    @JsonCreator
    public static RangedDoubleInt fromJson(JsonNode rootNode) throws JsonMappingException {
        if (rootNode.isInt()) {
            int value = rootNode.asInt();
            return forStaticValue(value);
        } else if (rootNode.isObject()) {
            OptionalDouble min = valueAsDouble(rootNode, "min");
            OptionalDouble max = valueAsDouble(rootNode, "max");
            if (min.isPresent() && max.isPresent()) {
                return forRangedValue(min.getAsDouble(), max.getAsDouble());
            }
        }
        throw new JsonMappingException(null, "Cannot create RangedDouble.");
    }

    public static RangedDoubleInt forStaticValue(int staticValue) {
        return new RangedDoubleInt(OptionalInt.of(staticValue), Optional.empty());
    }

    public static RangedDoubleInt forRangedValue(double min, double max) {
        return new RangedDoubleInt(OptionalInt.empty(), Optional.of(new RangedDouble(OptionalDouble.of(min), OptionalDouble.of(max))));
    }

    private final OptionalInt staticValue;
    private final Optional<RangedDouble> rangedValue;

    private RangedDoubleInt(OptionalInt staticValue, Optional<RangedDouble> rangedValue) {
        this.staticValue = staticValue;
        this.rangedValue = rangedValue;
    }

    public OptionalInt getStaticValue() {
        return staticValue;
    }

    public Optional<RangedDouble> getRangedValue() {
        return rangedValue;
    }
}
