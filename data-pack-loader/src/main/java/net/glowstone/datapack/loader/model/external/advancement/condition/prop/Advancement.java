package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Advancement {
    @JsonCreator
    public static Advancement fromJson(JsonNode rootNode) throws JsonMappingException {
        if (rootNode.isBoolean()) {
            return createWithEnabled(rootNode.asBoolean());
        } else if (rootNode.isObject()) {
            Map<String, Boolean> criteria = new HashMap<>();
            for (Map.Entry<String, JsonNode> entry : ImmutableList.copyOf(rootNode.fields())) {
                if (entry.getValue().isBoolean()) {
                    criteria.put(entry.getKey(), entry.getValue().asBoolean());
                }
            }
            if (criteria.size() == rootNode.size()) {
                return createWithCriteria(criteria);
            }
        }
        throw new JsonMappingException(null, "Cannot create Advancement");
    }

    public static Advancement createWithEnabled(boolean advancement) {
        return new Advancement(Optional.of(advancement), Optional.empty());
    }

    public static Advancement createWithCriteria(Map<String, Boolean> criteria) {
        return new Advancement(Optional.empty(), Optional.of(criteria));
    }

    private final Optional<Boolean> advancement;
    private final Optional<Map<String, Boolean>> criteria;

    private Advancement(Optional<Boolean> advancement, Optional<Map<String, Boolean>> criteria) {
        this.advancement = advancement;
        this.criteria = criteria;
    }

    public Optional<Boolean> getAdvancement() {
        return advancement;
    }

    public Optional<Map<String, Boolean>> getCriteria() {
        return criteria;
    }
}
