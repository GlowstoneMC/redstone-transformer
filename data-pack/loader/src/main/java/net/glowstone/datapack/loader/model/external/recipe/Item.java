package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

import java.util.Optional;

public class Item {
    private final Optional<String> item;
    private final Optional<String> tag;

    @JsonCreator
    public Item(
        @JsonProperty("item") Optional<String> item,
        @JsonProperty("tag") Optional<String> tag) {
        Preconditions.checkArgument(
            (item.isPresent() && !tag.isPresent()) || (!item.isPresent() && tag.isPresent()),
            "Either item or tag must be specified, but not both."
        );
        this.item = item;
        this.tag = tag;
    }

    public Optional<String> getItem() {
        return item;
    }

    public Optional<String> getTag() {
        return tag;
    }
}
