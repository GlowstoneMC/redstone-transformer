package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;

import java.util.Optional;

public class BredAnimalsConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:bred_animals";

    private final Optional<Entity> child;
    private final Optional<Entity> parent;
    private final Optional<Entity> partner;

    @JsonCreator
    public BredAnimalsConditions(
        @JsonProperty("child") Optional<Entity> child,
        @JsonProperty("parent") Optional<Entity> parent,
        @JsonProperty("partner") Optional<Entity> partner) {
        this.child = child;
        this.parent = parent;
        this.partner = partner;
    }

    public Optional<Entity> getChild() {
        return child;
    }

    public Optional<Entity> getParent() {
        return parent;
    }

    public Optional<Entity> getPartner() {
        return partner;
    }
}
