package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

public class SetStewEffectFunction extends Function {
    public static class Effect {
        private final String type;
        private final RangedInt duration;

        @JsonCreator
        public Effect(
            @JsonProperty("type") String type,
            @JsonProperty("duration") RangedInt duration) {
            this.type = type;
            this.duration = duration;
        }

        public String getType() {
            return type;
        }

        public RangedInt getDuration() {
            return duration;
        }
    }

    public static final String TYPE_ID = "minecraft:set_stew_effect";

    private final List<Effect> effects;

    @JsonCreator
    public SetStewEffectFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("effects") List<Effect> effects) {
        super(conditions);
        this.effects = effects;
    }

    public List<Effect> getEffects() {
        return effects;
    }
}
