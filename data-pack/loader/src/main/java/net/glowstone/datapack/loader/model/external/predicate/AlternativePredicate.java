package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AlternativePredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:alternative";

    private final List<Predicate> terms;

    @JsonCreator
    public AlternativePredicate(
        @JsonProperty("terms") List<Predicate> terms) {
        this.terms = terms;
    }

    public List<Predicate> getTerms() {
        return terms;
    }
}
