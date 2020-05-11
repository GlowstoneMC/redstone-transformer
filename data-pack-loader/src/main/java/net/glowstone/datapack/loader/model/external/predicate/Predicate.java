package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "condition"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AlternativePredicate.class, name = AlternativePredicate.TYPE_ID),
    @JsonSubTypes.Type(value = BlockStatePropertyPredicate.class, name = BlockStatePropertyPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = DamageSourcePropertiesPredicate.class, name = DamageSourcePropertiesPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = EntityPropertiesPredicate.class, name = EntityPropertiesPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = EntityScoresPredicate.class, name = EntityScoresPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = InvertedPredicate.class, name = InvertedPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = KilledByPlayerPredicate.class, name = KilledByPlayerPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = LocationCheckPredicate.class, name = LocationCheckPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = MatchToolPredicate.class, name = MatchToolPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = RandomChancePredicate.class, name = RandomChancePredicate.TYPE_ID),
    @JsonSubTypes.Type(value = RandomChanceWhileLootingPredicate.class, name = RandomChanceWhileLootingPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = ReferencePredicate.class, name = ReferencePredicate.TYPE_ID),
    @JsonSubTypes.Type(value = SurvivesExplosionPredicate.class, name = SurvivesExplosionPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = TableBonusPredicate.class, name = TableBonusPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = TimeCheckPredicate.class, name = TimeCheckPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = ToolEnchantmentPredicate.class, name = ToolEnchantmentPredicate.TYPE_ID),
    @JsonSubTypes.Type(value = WeatherCheckPredicate.class, name = WeatherCheckPredicate.TYPE_ID),
})
public interface Predicate {
}
