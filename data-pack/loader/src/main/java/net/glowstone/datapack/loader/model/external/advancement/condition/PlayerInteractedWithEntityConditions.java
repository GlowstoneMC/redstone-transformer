package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class PlayerInteractedWithEntityConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:player_interacted_with_entity";

    private final Optional<Item> item;
    private final Optional<List<Predicate>> entity;
    private final Optional<List<Predicate>> player;

    @JsonCreator
    public PlayerInteractedWithEntityConditions(
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("entity") Optional<List<Predicate>> entity,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.item = item;
        this.entity = entity;
        this.player = player;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public Optional<List<Predicate>> getEntity() {
        return entity;
    }

    public Optional<List<Predicate>> getPlayer() {
        return player;
    }
}
