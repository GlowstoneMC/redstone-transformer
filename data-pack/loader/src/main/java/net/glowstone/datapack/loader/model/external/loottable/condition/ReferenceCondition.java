package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReferenceCondition implements Condition {
    public static final String TYPE_ID = "minecraft:reference";

    private final String name;

    @JsonCreator
    public ReferenceCondition(
        @JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
