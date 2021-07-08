package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;

public class MatchToolPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:match_tool";

    private final Item predicate;

    @JsonCreator
    public MatchToolPredicate(
        @JsonProperty("predicate") Item predicate) {
        this.predicate = predicate;
    }

    public Item getPredicate() {
        return predicate;
    }
}
