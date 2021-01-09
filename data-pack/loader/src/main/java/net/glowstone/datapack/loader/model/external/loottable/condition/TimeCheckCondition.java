package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.util.OptionalInt;

public class TimeCheckCondition implements Condition {
    public static final String TYPE_ID = "minecraft:time_check";

    private final RangedInt value;
    private final OptionalInt period;

    @JsonCreator
    public TimeCheckCondition(
        @JsonProperty("value") RangedInt value,
        @JsonProperty("period") OptionalInt period) {
        this.value = value;
        this.period = period;
    }

    public RangedInt getValue() {
        return value;
    }

    public OptionalInt getPeriod() {
        return period;
    }
}
