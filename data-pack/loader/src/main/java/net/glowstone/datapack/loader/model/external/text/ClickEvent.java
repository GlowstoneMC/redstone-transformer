package net.glowstone.datapack.loader.model.external.text;

import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsString;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public class ClickEvent {
    @JsonCreator
    public static ClickEvent fromJson(JsonNode rootNode) throws JsonMappingException {
        return new ClickEvent(
            valueAsString(rootNode, "action").orElse(null),
            valueAsString(rootNode, "value").orElse(null)
        );
    }

    private final String action;
    private final String value;

    public ClickEvent(String action, String value) {
        this.action = action;
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public String getValue() {
        return value;
    }
}
