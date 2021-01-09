package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AlternativeCondition implements Condition {
    public static final String TYPE_ID = "minecraft:alternative";

    private final List<Condition> terms;

    @JsonCreator
    public AlternativeCondition(
        @JsonProperty("terms") List<Condition> terms) {
        this.terms = terms;
    }

    public List<Condition> getTerms() {
        return terms;
    }
}
