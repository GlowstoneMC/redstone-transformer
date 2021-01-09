package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.recipe.Item;

import java.util.Optional;

public class ShotCrossbowConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:shot_crossbow";

    private final Optional<Item> item;

    @JsonCreator
    public ShotCrossbowConditions(
        @JsonProperty("item") Optional<Item> item) {
        this.item = item;
    }

    public Optional<Item> getItem() {
        return item;
    }
}
