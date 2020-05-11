package net.glowstone.datapack.loader.model.external.text;

import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsObject;
import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsString;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public class HoverEvent {
    @JsonCreator
    public static HoverEvent fromJson(JsonNode rootNode) throws JsonMappingException {
        return new HoverEvent(
            valueAsString(rootNode, "action").orElse(null),
            valueAsObject(rootNode, "value", RawJsonText::fromJson).orElse(null)
        );
    }

    private final String action;
    private final RawJsonText value;

    public HoverEvent(String action, RawJsonText value) {
        this.action = action;
        this.value = value;
    }
}
