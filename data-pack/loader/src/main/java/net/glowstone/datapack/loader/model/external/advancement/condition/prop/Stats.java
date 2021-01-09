package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Stats {
    private final Optional<String> type;
    private final Optional<String> stat;
    private final Optional<RangedInt> value;

    @JsonCreator
    public Stats(
        @JsonProperty("type") Optional<String> type,
        @JsonProperty("stat") Optional<String> stat,
        @JsonProperty("value") Optional<RangedInt> value) {
        this.type = type;
        this.stat = stat;
        this.value = value;
    }

    public Optional<String> getType() {
        return type;
    }

    public Optional<String> getStat() {
        return stat;
    }

    public Optional<RangedInt> getValue() {
        return value;
    }
}
