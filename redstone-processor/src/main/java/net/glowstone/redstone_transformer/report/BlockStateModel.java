package net.glowstone.redstone_transformer.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class BlockStateModel {
    private final Map<String, String> properties;
    private final int id;
    private final boolean defaultState;

    @JsonCreator
    public BlockStateModel(
        @JsonProperty("properties") final Optional<Map<String, String>> properties,
        @JsonProperty("id") final Integer id,
        @JsonProperty("default") final Optional<Boolean> defaultState) {
        this.properties = properties.orElse(Collections.emptyMap());
        this.id = Objects.requireNonNull(id);
        this.defaultState = defaultState.orElse(false);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public int getId() {
        return id;
    }

    public boolean isDefaultState() {
        return defaultState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockStateModel that = (BlockStateModel) o;
        return id == that.id &&
            Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties, id);
    }
}
