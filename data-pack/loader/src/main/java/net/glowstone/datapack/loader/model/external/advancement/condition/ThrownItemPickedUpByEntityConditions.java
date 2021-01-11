package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Location;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class ThrownItemPickedUpByEntityConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:thrown_item_picked_up_by_entity";

    private final Optional<Item> item;
    private final Optional<List<Predicate>> entity;

    @JsonCreator
    public ThrownItemPickedUpByEntityConditions(
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("entity") Optional<List<Predicate>> entity,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.item = item;
        this.entity = entity;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public Optional<List<Predicate>> getEntity() {
        return entity;
    }
}
