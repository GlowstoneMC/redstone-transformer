package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReferencePredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:reference";

    private final String name;

    @JsonCreator
    public ReferencePredicate(
        @JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
