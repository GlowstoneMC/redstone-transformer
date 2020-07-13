package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

import java.util.OptionalInt;

public class CraftingResult {
    private final int count;
    private final String item;

    @JsonCreator
    public CraftingResult(
        @JsonProperty(value = "count") OptionalInt count,
        @JsonProperty("item") String item) {
        this.count = count.orElse(1);
        this.item = item;

        Preconditions.checkArgument(this.count > 0, "count must be greater than 0");
    }

    public int getCount() {
        return count;
    }

    public String getItem() {
        return item;
    }
}
