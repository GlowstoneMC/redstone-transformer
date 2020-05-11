package net.glowstone.datapack.loader.model.external.mcmeta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class McMeta {
    private final McMetaPack pack;

    @JsonCreator
    public McMeta(@JsonProperty("pack") McMetaPack pack) {
        this.pack = pack;
    }

    public McMetaPack getPack() {
        return pack;
    }
}
