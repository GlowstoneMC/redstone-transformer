package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherCheckCondition implements Condition {
    public static final String TYPE_ID = "minecraft:weather_check";

    private final boolean raining;
    private final boolean thundering;

    @JsonCreator
    public WeatherCheckCondition(
        @JsonProperty("raining") boolean raining,
        @JsonProperty("thundering") boolean thundering) {
        this.raining = raining;
        this.thundering = thundering;
    }

    public boolean isRaining() {
        return raining;
    }

    public boolean isThundering() {
        return thundering;
    }
}
