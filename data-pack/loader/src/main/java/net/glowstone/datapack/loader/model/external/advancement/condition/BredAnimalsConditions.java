package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class BredAnimalsConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:bred_animals";

    private final Optional<List<Predicate>> child;
    private final Optional<List<Predicate>> parent;
    private final Optional<List<Predicate>> partner;

    @JsonCreator
    public BredAnimalsConditions(
        @JsonProperty("child") Optional<List<Predicate>> child,
        @JsonProperty("parent") Optional<List<Predicate>> parent,
        @JsonProperty("partner") Optional<List<Predicate>> partner,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.child = child;
        this.parent = parent;
        this.partner = partner;
    }

    public Optional<List<Predicate>> getChild() {
        return child;
    }

    public Optional<List<Predicate>> getParent() {
        return parent;
    }

    public Optional<List<Predicate>> getPartner() {
        return partner;
    }
}
