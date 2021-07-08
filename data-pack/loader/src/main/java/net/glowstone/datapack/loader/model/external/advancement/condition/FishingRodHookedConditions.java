package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class FishingRodHookedConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:fishing_rod_hooked";

    private final Optional<List<Predicate>> entity;
    private final Optional<Item> item;
    private final Optional<Item> rod;

    @JsonCreator
    public FishingRodHookedConditions(
        @JsonProperty("entity") Optional<List<Predicate>> entity,
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("rod") Optional<Item> rod,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.entity = entity;
        this.item = item;
        this.rod = rod;
    }

    public Optional<List<Predicate>> getEntity() {
        return entity;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public Optional<Item> getRod() {
        return rod;
    }
}
