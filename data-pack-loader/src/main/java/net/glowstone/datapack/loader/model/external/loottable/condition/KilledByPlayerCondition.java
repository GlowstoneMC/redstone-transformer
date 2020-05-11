package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KilledByPlayerCondition implements Condition {
    public static final String TYPE_ID = "minecraft:killed_by_player";

    private final boolean inverse;

    @JsonCreator
    public KilledByPlayerCondition(
        @JsonProperty("inverse") boolean inverse) {
        this.inverse = inverse;
    }

    public boolean isInverse() {
        return inverse;
    }
}
