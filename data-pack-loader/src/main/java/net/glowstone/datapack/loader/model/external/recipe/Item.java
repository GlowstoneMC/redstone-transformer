package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private final String item;
    private final String tag;

    @JsonCreator
    public Item(
        @JsonProperty("item") String item,
        @JsonProperty("tag") String tag) {
        this.item = item;
        this.tag = tag;
    }

    public String getItem() {
        return item;
    }

    public String getTag() {
        return tag;
    }
}
