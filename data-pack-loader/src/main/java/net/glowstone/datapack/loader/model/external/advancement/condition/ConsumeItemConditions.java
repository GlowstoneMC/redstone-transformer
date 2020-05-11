package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;

import java.util.Optional;

public class ConsumeItemConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:consume_item";
    
    private final Optional<Item> item;

    @JsonCreator
    public ConsumeItemConditions(
        @JsonProperty("item") Optional<Item> item) {
        this.item = item;
    }

    public Optional<Item> getItem() {
        return item;
    }
}
