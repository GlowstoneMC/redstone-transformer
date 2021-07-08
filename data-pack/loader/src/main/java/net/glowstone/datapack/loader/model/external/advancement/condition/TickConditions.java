package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class TickConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:tick";

    @JsonCreator
    public TickConditions(
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
    }
}
