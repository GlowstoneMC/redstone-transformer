package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;

import java.util.Optional;

public class FishingRodHookedConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:fishing_rod_hooked";

    private final Optional<Entity> entity;
    private final Optional<Item> item;
    private final Optional<Item> rod;

    @JsonCreator
    public FishingRodHookedConditions(
        @JsonProperty("entity") Optional<Entity> entity,
        @JsonProperty("item") Optional<Item> item,
        @JsonProperty("rod") Optional<Item> rod) {
        this.entity = entity;
        this.item = item;
        this.rod = rod;
    }

    public Optional<Entity> getEntity() {
        return entity;
    }

    public Optional<Item> getItem() {
        return item;
    }

    public Optional<Item> getRod() {
        return rod;
    }
}
