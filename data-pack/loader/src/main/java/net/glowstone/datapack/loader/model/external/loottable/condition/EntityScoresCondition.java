package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;
import net.glowstone.datapack.loader.model.external.loottable.SourceEntity;

import java.util.Map;

public class EntityScoresCondition implements Condition {
    public static final String TYPE_ID = "minecraft:entity_scores";

    private final SourceEntity entity;
    private final Map<String, RangedInt> scores;

    @JsonCreator
    public EntityScoresCondition(
        @JsonProperty("entity") SourceEntity entity,
        @JsonProperty("scores") Map<String, RangedInt> scores) {
        this.entity = entity;
        this.scores = scores;
    }

    public SourceEntity getEntity() {
        return entity;
    }

    public Map<String, RangedInt> getScores() {
        return scores;
    }
}
