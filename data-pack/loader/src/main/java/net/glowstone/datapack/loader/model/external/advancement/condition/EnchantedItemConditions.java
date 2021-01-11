package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class EnchantedItemConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:enchanted_item";

    private final Optional<Item> item;
    private final Optional<RangedInt> levels;

    @JsonCreator
    public EnchantedItemConditions(
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("levels") Optional<RangedInt> levels,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
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
