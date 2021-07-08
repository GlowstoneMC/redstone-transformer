package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Equipment {
    private final Optional<Item> mainhand;
    private final Optional<Item> offhand;
    private final Optional<Item> head;
    private final Optional<Item> chest;
    private final Optional<Item> legs;
    private final Optional<Item> feet;

    @JsonCreator
    public Equipment(
        @JsonProperty("mainhand") Optional<Item> mainhand,
        @JsonProperty("offhand") Optional<Item> offhand,
        @JsonProperty("head") Optional<Item> head,
        @JsonProperty("chest") Optional<Item> chest,
        @JsonProperty("legs") Optional<Item> legs,
        @JsonProperty("feet") Optional<Item> feet) {
        this.mainhand = mainhand;
        this.offhand = offhand;
        this.head = head;
        this.chest = chest;
        this.legs = legs;
        this.feet = feet;
    }

    public Optional<Item> getMainhand() {
        return mainhand;
    }

    public Optional<Item> getOffhand() {
        return offhand;
    }

    public Optional<Item> getHead() {
        return head;
    }

    public Optional<Item> getChest() {
        return chest;
    }

    public Optional<Item> getLegs() {
        return legs;
    }

    public Optional<Item> getFeet() {
        return feet;
    }
}
