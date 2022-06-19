package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;

import java.util.Optional;

public class Item {
    @JsonCreator
    public static Item fromJson(JsonNode rootNode) throws JsonMappingException {
        if (rootNode.isTextual()) {
            return new Item(Optional.of(rootNode.textValue()), Optional.empty());
        } else if (rootNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) rootNode;

            Optional<String> item = Optional.empty();
            Optional<String> tag = Optional.empty();

            if (objectNode.has("items")) {
                JsonNode itemsNode = objectNode.get("items");
                if (itemsNode.isArray()) {
                    ArrayNode arrayNode = objectNode.withArray("items");
                    if (arrayNode.size() == 1) {
                        JsonNode itemNode = arrayNode.get(0);
                        if (itemNode.isTextual()) {
                            item = Optional.of(itemNode.textValue());
                        } else {
                            throw new JsonMappingException(null, "Cannot create Item, 'item' property is not textual.");
                        }
                    } else {
                        throw new JsonMappingException(null, "Cannot create Item, 'items' property does not have single item.");
                    }
                } else {
                    throw new JsonMappingException(null, "Cannot create Item, 'items' property is not an array.");
                }
            }

            if (objectNode.has("tag")) {
                JsonNode tagNode = objectNode.get("tag");
                if (tagNode.isTextual()) {
                    tag = Optional.of(tagNode.textValue());
                } else {
                    throw new JsonMappingException(null, "Cannot create Item, 'tag' property is not text.");
                }
            }

            return new Item(item, tag);
        }

        throw new JsonMappingException(null, "Cannot create Item, not an object or text");
    }

    private final Optional<String> item;
    private final Optional<String> tag;

    public Item(
        Optional<String> item,
        Optional<String> tag) {
        System.out.println(item);
        System.out.println(tag);
        Preconditions.checkArgument(
            (item.isPresent() && !tag.isPresent()) || (!item.isPresent() && tag.isPresent()),
            "Either item or tag must be specified, but not both."
        );
        this.item = item;
        this.tag = tag;
    }

    public Optional<String> getItem() {
        return item;
    }

    public Optional<String> getTag() {
        return tag;
    }
}
