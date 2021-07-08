package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Location;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class ItemUsedOnBlockConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:item_used_on_block";

    private final Optional<Location> location;
    private final Optional<Item> item;
    private final Optional<List<Predicate>> player;

    @JsonCreator
    public ItemUsedOnBlockConditions(
        @JsonProperty("location") Optional<Location> location,
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.location = location;
        this.item = item;
        this.player = player;
    }

    public Optional<Location> getLocation() {
        return location;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public Optional<List<Predicate>> getPlayer() {
        return player;
    }
}
