package net.glowstone.datapack.loader.jackson;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.math.DoubleMath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class JsonNodeHandling {
    public static Optional<String> valueAsString(JsonNode rootNode, String propName) throws JsonMappingException {
        if (!rootNode.has(propName)) {
            return Optional.empty();
        }
        JsonNode valueNode = rootNode.get(propName);
        if (!valueNode.isTextual()) {
            throw new JsonMappingException(null, "Cannot deserialize string prop " + propName);
        }
        return Optional.of(valueNode.textValue());
    }

    public static Optional<Boolean> valueAsBoolean(JsonNode rootNode, String propName) throws JsonMappingException {
        if (!rootNode.has(propName)) {
            return Optional.empty();
        }
        JsonNode valueNode = rootNode.get(propName);
        if (!valueNode.isBoolean()) {
            throw new JsonMappingException(null, "Cannot deserialize boolean prop " + propName);
        }
        return Optional.of(valueNode.booleanValue());
    }

    public static OptionalInt valueAsInt(JsonNode rootNode, String propName) throws JsonMappingException {
        if (!rootNode.has(propName)) {
            return OptionalInt.empty();
        }
        JsonNode valueNode = rootNode.get(propName);
        if (valueNode.isInt()) {
            return OptionalInt.of(valueNode.intValue());
        } else if (valueNode.isDouble()) {
            double value = valueNode.asDouble();
            if (DoubleMath.isMathematicalInteger(value)) {
                return OptionalInt.of((int)value);
            }
        }
        throw new JsonMappingException(null, "Cannot deserialize int prop " + propName);

    }

    public static OptionalDouble valueAsDouble(JsonNode rootNode, String propName) throws JsonMappingException {
        if (!rootNode.has(propName)) {
            return OptionalDouble.empty();
        }
        JsonNode valueNode = rootNode.get(propName);
        if (!valueNode.isDouble()) {
            throw new JsonMappingException(null, "Cannot deserialize double prop " + propName);
        }
        return OptionalDouble.of(valueNode.doubleValue());
    }

    public static <T> Optional<T> valueAsObject(JsonNode rootNode, String propName, JsonNodeMapper<T> delegate) throws JsonMappingException {
        if (!rootNode.has(propName)) {
            return Optional.empty();
        }
        JsonNode valueNode = rootNode.get(propName);
        if (!valueNode.isObject()) {
            throw new JsonMappingException(null, "Cannot deserialize array prop " + propName);
        }
        return Optional.of(delegate.apply(valueNode));
    }

    public static <T> Optional<List<T>> valueAsList(JsonNode rootNode, String propName, JsonNodeMapper<T> delegate) throws JsonMappingException {
        if (!rootNode.has(propName)) {
            return Optional.empty();
        }
        JsonNode valueNode = rootNode.get(propName);
        if (!valueNode.isArray()) {
            throw new JsonMappingException(null, "Cannot deserialize array prop " + propName);
        }
        List<T> rawJsonTexts = new ArrayList<>(valueNode.size());
        for (int i = 0; i < valueNode.size(); i++) {
            rawJsonTexts.add(delegate.apply(valueNode.get(i)));
        }
        return Optional.of(rawJsonTexts);
    }

    @FunctionalInterface
    public interface JsonNodeMapper<T> {
        T apply(JsonNode rootNode) throws JsonMappingException;
    }
}
