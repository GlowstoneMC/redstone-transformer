package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public class PropertyValue<T> {
    @JsonCreator
    public static PropertyValue<?> fromJson(JsonNode rootNode) throws JsonMappingException {
        if (rootNode.isBoolean()) {
            return new PropertyValue<>(rootNode.asBoolean());
        } else if (rootNode.isInt()) {
            return new PropertyValue<>(rootNode.asInt());
        } else if (rootNode.isTextual()) {
            return new PropertyValue<>(rootNode.asText());
        } else if (rootNode.isObject()) {
            return new PropertyValue<>(RangedInt.fromJson(rootNode));
        }
        throw new JsonMappingException(null, "Cannot create PropertyValue");
    }

    private final T value;

    public PropertyValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
