package net.glowstone.datapack.loader.model.external.text;

import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsString;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public class Score {
    @JsonCreator
    public static Score fromJson(JsonNode rootNode) throws JsonMappingException {
        return new Score(
            valueAsString(rootNode, "name").orElse(null),
            valueAsString(rootNode, "objective").orElse(null),
            valueAsString(rootNode, "value").orElse(null)
        );
    }

    private final String name;
    private final String objective;
    private final String value;

    public Score(String name, String objective, String value) {
        this.name = name;
        this.objective = objective;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getObjective() {
        return objective;
    }

    public String getValue() {
        return value;
    }
}
