package net.glowstone.datapack.loader.model.external.mcmeta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class McMetaPack {
    private final int packFormat;
    private final String description;

    @JsonCreator
    public McMetaPack(
        @JsonProperty("pack_format") int packFormat,
        @JsonProperty("description") String description) {
        this.packFormat = packFormat;
        this.description = description;
    }

    public int getPackFormat() {
        return packFormat;
    }

    public String getDescription() {
        return description;
    }
}
