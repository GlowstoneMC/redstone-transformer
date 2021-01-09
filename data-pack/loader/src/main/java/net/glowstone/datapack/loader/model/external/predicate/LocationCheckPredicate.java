package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Location;

import java.util.OptionalInt;

public class LocationCheckPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:location_check";

    private final OptionalInt offsetX;
    private final OptionalInt offsetY;
    private final OptionalInt offsetZ;
    private final Location predicate;

    @JsonCreator
    public LocationCheckPredicate(
        @JsonProperty("offsetX") OptionalInt offsetX,
        @JsonProperty("offsetY") OptionalInt offsetY,
        @JsonProperty("offsetZ") OptionalInt offsetZ,
        @JsonProperty("predicate") Location predicate) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.predicate = predicate;
    }

    public OptionalInt getOffsetX() {
        return offsetX;
    }

    public OptionalInt getOffsetY() {
        return offsetY;
    }

    public OptionalInt getOffsetZ() {
        return offsetZ;
    }

    public Location getPredicate() {
        return predicate;
    }
}
