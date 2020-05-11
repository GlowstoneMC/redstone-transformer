package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.recipe.Item;

import java.util.Optional;

public class UsedTotemConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:used_totem";

    private final Optional<Item> item;

    @JsonCreator
    public UsedTotemConditions(
        @JsonProperty("item") Optional<Item> item) {
        this.item = item;
    }

    public Optional<Item> getItem() {
        return item;
    }
}
