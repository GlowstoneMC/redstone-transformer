package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InvertedPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:inverted";

    private final Predicate inverted;

    @JsonCreator
    public InvertedPredicate(
        @JsonProperty("inverted") Predicate inverted) {
        this.inverted = inverted;
    }

    public Predicate getInverted() {
        return inverted;
    }
}
