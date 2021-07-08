package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.util.Map;

public class EntityScoresPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:entity_scores";

    private final String entity;
    private final Map<String, RangedInt> scores;

    @JsonCreator
    public EntityScoresPredicate(
        @JsonProperty("entity") String entity,
        @JsonProperty("scores") Map<String, RangedInt> scores) {
        this.entity = entity;
        this.scores = scores;
    }

    public String getEntity() {
        return entity;
    }

    public Map<String, RangedInt> getScores() {
        return scores;
    }
}
