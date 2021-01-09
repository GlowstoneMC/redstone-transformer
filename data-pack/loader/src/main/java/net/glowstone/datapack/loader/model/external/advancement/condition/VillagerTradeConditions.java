package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;
import net.glowstone.datapack.loader.model.external.recipe.Item;

import java.util.Optional;

public class VillagerTradeConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:villager_trade";

    private final Optional<Item> item;
    private final Optional<Entity> villager;

    @JsonCreator
    public VillagerTradeConditions(
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("villager") Optional<Entity> villager) {
        this.item = item;
        this.villager = villager;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public Optional<Entity> getVillager() {
        return villager;
    }
}
