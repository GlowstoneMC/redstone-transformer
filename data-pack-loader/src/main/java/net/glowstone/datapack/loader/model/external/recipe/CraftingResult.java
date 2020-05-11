package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CraftingResult {
    private final int count;
    private final String item;

    public CraftingResult(
        @JsonProperty(value = "count", defaultValue = "1") int count,
        @JsonProperty("item") String item) {
        this.count = count;
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public String getItem() {
        return item;
    }
}
