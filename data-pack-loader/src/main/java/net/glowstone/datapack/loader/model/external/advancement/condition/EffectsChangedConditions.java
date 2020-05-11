package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Effect;

import java.util.Map;

public class EffectsChangedConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:effects_changed";

    private final Map<String, Effect> effects;

    @JsonCreator
    public EffectsChangedConditions(
        @JsonProperty("effects") Map<String, Effect> effects) {
        this.effects = effects;
    }

    public Map<String, Effect> getEffects() {
        return effects;
    }
}
