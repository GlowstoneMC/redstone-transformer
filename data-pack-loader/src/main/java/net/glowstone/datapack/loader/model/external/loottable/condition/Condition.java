package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "condition"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AlternativeCondition.class, name = AlternativeCondition.TYPE_ID),
    @JsonSubTypes.Type(value = BlockStatePropertyCondition.class, name = BlockStatePropertyCondition.TYPE_ID),
    @JsonSubTypes.Type(value = DamageSourcePropertiesCondition.class, name = DamageSourcePropertiesCondition.TYPE_ID),
    @JsonSubTypes.Type(value = EntityPropertiesCondition.class, name = EntityPropertiesCondition.TYPE_ID),
    @JsonSubTypes.Type(value = EntityScoresCondition.class, name = EntityScoresCondition.TYPE_ID),
    @JsonSubTypes.Type(value = InvertedCondition.class, name = InvertedCondition.TYPE_ID),
    @JsonSubTypes.Type(value = KilledByPlayerCondition.class, name = KilledByPlayerCondition.TYPE_ID),
    @JsonSubTypes.Type(value = LocationCheckCondition.class, name = LocationCheckCondition.TYPE_ID),
    @JsonSubTypes.Type(value = MatchToolCondition.class, name = MatchToolCondition.TYPE_ID),
    @JsonSubTypes.Type(value = RandomChanceCondition.class, name = RandomChanceCondition.TYPE_ID),
    @JsonSubTypes.Type(value = RandomChanceWithLootingCondition.class, name = RandomChanceWithLootingCondition.TYPE_ID),
    @JsonSubTypes.Type(value = ReferenceCondition.class, name = ReferenceCondition.TYPE_ID),
    @JsonSubTypes.Type(value = SurvivesExplosionCondition.class, name = SurvivesExplosionCondition.TYPE_ID),
    @JsonSubTypes.Type(value = TableBonusCondition.class, name = TableBonusCondition.TYPE_ID),
    @JsonSubTypes.Type(value = TimeCheckCondition.class, name = TimeCheckCondition.TYPE_ID),
    @JsonSubTypes.Type(value = ToolEnchantmentCondition.class, name = ToolEnchantmentCondition.TYPE_ID),
    @JsonSubTypes.Type(value = WeatherCheckCondition.class, name = WeatherCheckCondition.TYPE_ID),
})
public interface Condition {
}
