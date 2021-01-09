package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.util.Optional;

public class EnchantedItemConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:enchanted_item";

    private final Optional<Item> item;
    private final Optional<RangedInt> levels;

    @JsonCreator
    public EnchantedItemConditions(
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("levels") Optional<RangedInt> levels) {
        this.item = item;
        this.levels = levels;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public Optional<RangedInt> getLevels() {
        return levels;
    }
}
