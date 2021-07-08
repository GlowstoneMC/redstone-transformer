package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InvertedPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:inverted";

    private final Predicate term;

    @JsonCreator
    public InvertedPredicate(
        @JsonProperty("term") Predicate term) {
        this.term = term;
    }

    public Predicate getTerm() {
        return term;
    }
}
