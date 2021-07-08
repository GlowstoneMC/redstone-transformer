package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;
import net.glowstone.datapack.loader.model.external.recipe.Item;

import java.util.List;
import java.util.Optional;

public class VillagerTradeConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:villager_trade";

    private final Optional<Item> item;
    private final Optional<List<Predicate>> villager;

    @JsonCreator
    public VillagerTradeConditions(
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("villager") Optional<List<Predicate>> villager,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.item = item;
        this.villager = villager;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public Optional<List<Predicate>> getVillager() {
        return villager;
    }
}
