package net.glowstone.datapack.loader.model.external.advancement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Icon {
    private final String item;
    private final String nbt;

    @JsonCreator
    public Icon(
        @JsonProperty("item") String item,
        @JsonProperty("nbt") String nbt) {
        this.item = item;
        this.nbt = nbt;
    }

    public String getItem() {
        return item;
    }

    public String getNbt() {
        return nbt;
    }
}
