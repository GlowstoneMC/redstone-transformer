package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;

public class MatchToolCondition implements Condition {
    public static final String TYPE_ID = "minecraft:match_tool";

    private final Item predicate;

    @JsonCreator
    public MatchToolCondition(
        @JsonProperty("item") Item predicate) {
        this.predicate = predicate;
    }

    public Item getPredicate() {
        return predicate;
    }
}
