package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class ItemId {
    @JsonCreator
    public static ItemId fromJson(JsonNode rootNode) throws JsonMappingException {
        if (rootNode.isTextual()) {
            return new ItemId(Optional.of(rootNode.asText()));
        } else if (rootNode.isObject()) {
            JsonNode itemNode = rootNode.get("item");
            if (itemNode != null && itemNode.isTextual()) {
                return new ItemId(Optional.of(itemNode.asText()));
            } else if (itemNode == null || itemNode.isNull()) {
                return new ItemId(Optional.empty());
            }
        } else if (rootNode.isNull()) {
            return new ItemId(Optional.empty());
        }
        throw new JsonMappingException(null, "Cannot create ItemId");
    }

    private final Optional<String> item;

    public ItemId(Optional<String> item) {
        this.item = item;
    }

    public Optional<String> getItem() {
        return item;
    }
}
