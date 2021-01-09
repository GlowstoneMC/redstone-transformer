package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "function"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ApplyBonusFunction.class, name = ApplyBonusFunction.TYPE_ID),
    @JsonSubTypes.Type(value = CopyNameFunction.class, name = CopyNameFunction.TYPE_ID),
    @JsonSubTypes.Type(value = CopyNbtFunction.class, name = CopyNbtFunction.TYPE_ID),
    @JsonSubTypes.Type(value = CopyStateFunction.class, name = CopyStateFunction.TYPE_ID),
    @JsonSubTypes.Type(value = EnchantRandomlyFunction.class, name = EnchantRandomlyFunction.TYPE_ID),
    @JsonSubTypes.Type(value = EnchantWithLevelsFunction.class, name = EnchantWithLevelsFunction.TYPE_ID),
    @JsonSubTypes.Type(value = ExplorationMapFunction.class, name = ExplorationMapFunction.TYPE_ID),
    @JsonSubTypes.Type(value = ExplosionDecayFunction.class, name = ExplosionDecayFunction.TYPE_ID),
    @JsonSubTypes.Type(value = FurnaceSmeltFunction.class, name = FurnaceSmeltFunction.TYPE_ID),
    @JsonSubTypes.Type(value = FillPlayerHeadFunction.class, name = FillPlayerHeadFunction.TYPE_ID),
    @JsonSubTypes.Type(value = LimitCountFunction.class, name = LimitCountFunction.TYPE_ID),
    @JsonSubTypes.Type(value = LootingEnchantFunction.class, name = LootingEnchantFunction.TYPE_ID),
    @JsonSubTypes.Type(value = SetAttributesFunction.class, name = SetAttributesFunction.TYPE_ID),
    @JsonSubTypes.Type(value = SetContentsFunction.class, name = SetContentsFunction.TYPE_ID),
    @JsonSubTypes.Type(value = SetCountFunction.class, name = SetCountFunction.TYPE_ID),
    @JsonSubTypes.Type(value = SetDamageFunction.class, name = SetDamageFunction.TYPE_ID),
    @JsonSubTypes.Type(value = SetLoreFunction.class, name = SetLoreFunction.TYPE_ID),
    @JsonSubTypes.Type(value = SetNameFunction.class, name = SetNameFunction.TYPE_ID),
    @JsonSubTypes.Type(value = SetNbtFunction.class, name = SetNbtFunction.TYPE_ID),
    @JsonSubTypes.Type(value = SetStewEffectFunction.class, name = SetStewEffectFunction.TYPE_ID),
})
public abstract class Function {
    private final List<Condition> conditions;

    public Function(
        List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Condition> getCondition() {
        return conditions;
    }
}
