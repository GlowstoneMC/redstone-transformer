package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InvertedCondition implements Condition {
    public static final String TYPE_ID = "minecraft:inverted";

    private final Condition term;

    @JsonCreator
    public InvertedCondition(
        @JsonProperty("term") Condition term) {
        this.term = term;
    }

    public Condition getTerm() {
        return term;
    }
}
