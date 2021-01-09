package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedDouble;

import java.util.List;

public class SetDamageFunction extends Function {
    public static final String TYPE_ID = "minecraft:set_damage";

    private final RangedDouble damage;

    @JsonCreator
    public SetDamageFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("damage") RangedDouble damage) {
        super(conditions);
        this.damage = damage;
    }

    public RangedDouble getDamage() {
        return damage;
    }
}
