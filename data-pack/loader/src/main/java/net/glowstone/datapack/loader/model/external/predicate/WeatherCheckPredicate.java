package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherCheckPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:weather_check";

    private final boolean raining;
    private final boolean thundering;

    @JsonCreator
    public WeatherCheckPredicate(
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
