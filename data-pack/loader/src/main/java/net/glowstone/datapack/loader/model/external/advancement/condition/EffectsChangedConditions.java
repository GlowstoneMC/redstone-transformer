package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Effect;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EffectsChangedConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:effects_changed";

    private final Map<String, Effect> effects;

    @JsonCreator
    public EffectsChangedConditions(
        @JsonProperty("effects") Map<String, Effect> effects,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.effects = effects;
    }

    public Map<String, Effect> getEffects() {
        return effects;
    }
}
