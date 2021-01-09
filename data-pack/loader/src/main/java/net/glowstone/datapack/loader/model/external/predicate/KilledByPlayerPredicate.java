package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KilledByPlayerPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:killed_by_player";

    private final boolean inverse;

    @JsonCreator
    public KilledByPlayerPredicate(
        @JsonProperty("inverse") boolean inverse) {
        this.inverse = inverse;
    }

    public boolean getInverse() {
        return inverse;
    }
}
