package net.glowstone.processor.block.data.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class BlockReportModel {
    private final Map<String, Set<String>> properties;
    private final Set<BlockStateModel> states;

    @JsonCreator
    public BlockReportModel(
        @JsonProperty("properties") final Optional<Map<String, Set<String>>> properties,
        @JsonProperty("states") final Set<BlockStateModel> states) {
        this.properties = properties.orElse(Collections.emptyMap());
        this.states = Objects.requireNonNull(states);
    }

    public Map<String, Set<String>> getProperties() {
        return properties;
    }

    public Set<BlockStateModel> getStates() {
        return states;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockReportModel that = (BlockReportModel) o;
        return Objects.equals(properties, that.properties) &&
            Objects.equals(states, that.states);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties, states);
    }
}
