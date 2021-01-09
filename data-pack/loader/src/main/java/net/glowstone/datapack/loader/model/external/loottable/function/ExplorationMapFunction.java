package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ExplorationMapFunction extends Function {
    public static final String TYPE_ID = "minecraft:exploration_map";

    private final String destination;
    private final String decoration;
    private final int zoom;
    private final int searchRadius;
    private final boolean skipExistingChunks;

    @JsonCreator
    public ExplorationMapFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("destination") String destination,
        @JsonProperty("decoration") String decoration,
        @JsonProperty("zoom") OptionalInt zoom,
        @JsonProperty("search_radius") OptionalInt searchRadius,
        @JsonProperty("skip_existing_chunks") Optional<Boolean> skipExistingChunks) {
        super(conditions);
        this.destination = destination;
        this.decoration = decoration;
        this.zoom = zoom.orElse(2);
        this.searchRadius = searchRadius.orElse(50);
        this.skipExistingChunks = skipExistingChunks.orElse(true);
    }

    public String getDestination() {
        return destination;
    }

    public String getDecoration() {
        return decoration;
    }

    public int getZoom() {
        return zoom;
    }

    public int getSearchRadius() {
        return searchRadius;
    }

    public boolean isSkipExistingChunks() {
        return skipExistingChunks;
    }
}
