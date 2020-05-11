package net.glowstone.datapack.loader.model.external.advancement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import net.glowstone.datapack.loader.model.external.advancement.condition.BeeNestDestroyedConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.BredAnimalsConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.BrewedPotionConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.ChangedDimensionConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.ChanneledLightningConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.Conditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.ConstructBeaconConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.ConsumeItemConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.CuredZombieVillagerConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.EffectsChangedConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.EnchantedItemConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.EnterBlockConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.EntityHurtPlayerConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.EntityKilledPlayerConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.FilledBucketConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.FishingRodHookedConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.HeroOfTheVillageConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.ImpossibleConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.InventoryChangedConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.ItemDurabilityChangedConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.KilledByCrossbowConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.LevitationConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.LocationConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.NetherTravelConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.PlacedBlockConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.PlayerHurtEntityConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.PlayerKilledEntityConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.RecipeUnlockedConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.SafelyHarvestHoneyConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.ShotCrossbowConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.SleptInBedConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.SlideDownBlockConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.SummonedEntityConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.TameAnimalConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.TickConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.UsedEnderEyeConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.UsedTotemConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.VillagerTradeConditions;
import net.glowstone.datapack.loader.model.external.advancement.condition.VoluntaryExileConditions;

import java.io.IOException;

@JsonDeserialize(using = Criteria.CriteriaDeserializer.class)
public class Criteria {
    public static class CriteriaDeserializer extends StdDeserializer<Criteria> {
        public CriteriaDeserializer() {
            super(Criteria.class);
        }

        @Override
        public Criteria deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            ObjectCodec codec = p.getCodec();

            if (p.getCurrentToken() == JsonToken.START_OBJECT) {
                JsonNode rootNode = p.readValueAsTree();
                JsonNode typeNode = rootNode.get("trigger");
                JsonNode conditionsNode = rootNode.get("conditions");

                if (conditionsNode == null) {
                    conditionsNode = (JsonNode) codec.createObjectNode();
                }

                if (typeNode != null && typeNode.isTextual() && conditionsNode.isObject()) {
                    Conditions conditions = null;
                    switch (typeNode.asText()) {
                        case BeeNestDestroyedConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, BeeNestDestroyedConditions.class);
                            break;

                        case BredAnimalsConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, BredAnimalsConditions.class);
                            break;

                        case BrewedPotionConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, BrewedPotionConditions.class);
                            break;

                        case ChangedDimensionConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, ChangedDimensionConditions.class);
                            break;

                        case ChanneledLightningConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, ChanneledLightningConditions.class);
                            break;

                        case ConstructBeaconConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, ConstructBeaconConditions.class);
                            break;

                        case ConsumeItemConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, ConsumeItemConditions.class);
                            break;

                        case CuredZombieVillagerConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, CuredZombieVillagerConditions.class);
                            break;

                        case EffectsChangedConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, EffectsChangedConditions.class);
                            break;

                        case EnchantedItemConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, EnchantedItemConditions.class);
                            break;

                        case EnterBlockConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, EnterBlockConditions.class);
                            break;

                        case EntityHurtPlayerConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, EntityHurtPlayerConditions.class);
                            break;

                        case EntityKilledPlayerConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, EntityKilledPlayerConditions.class);
                            break;

                        case FilledBucketConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, FilledBucketConditions.class);
                            break;

                        case FishingRodHookedConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, FishingRodHookedConditions.class);
                            break;

                        case HeroOfTheVillageConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, HeroOfTheVillageConditions.class);
                            break;

                        case ImpossibleConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, ImpossibleConditions.class);
                            break;

                        case InventoryChangedConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, InventoryChangedConditions.class);
                            break;

                        case ItemDurabilityChangedConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, ItemDurabilityChangedConditions.class);
                            break;

                        case KilledByCrossbowConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, KilledByCrossbowConditions.class);
                            break;

                        case LevitationConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, LevitationConditions.class);
                            break;

                        case LocationConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, LocationConditions.class);
                            break;

                        case NetherTravelConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, NetherTravelConditions.class);
                            break;

                        case PlacedBlockConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, PlacedBlockConditions.class);
                            break;

                        case PlayerHurtEntityConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, PlayerHurtEntityConditions.class);
                            break;

                        case PlayerKilledEntityConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, PlayerKilledEntityConditions.class);
                            break;

                        case RecipeUnlockedConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, RecipeUnlockedConditions.class);
                            break;

                        case SafelyHarvestHoneyConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, SafelyHarvestHoneyConditions.class);
                            break;

                        case ShotCrossbowConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, ShotCrossbowConditions.class);
                            break;

                        case SleptInBedConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, SleptInBedConditions.class);
                            break;

                        case SlideDownBlockConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, SlideDownBlockConditions.class);
                            break;

                        case SummonedEntityConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, SummonedEntityConditions.class);
                            break;

                        case TameAnimalConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, TameAnimalConditions.class);
                            break;

                        case TickConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, TickConditions.class);
                            break;

                        case UsedEnderEyeConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, UsedEnderEyeConditions.class);
                            break;

                        case UsedTotemConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, UsedTotemConditions.class);
                            break;

                        case VillagerTradeConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, VillagerTradeConditions.class);
                            break;

                        case VoluntaryExileConditions.TYPE_ID:
                            conditions = codec.treeToValue(conditionsNode, VoluntaryExileConditions.class);
                            break;
                    }
                    if (conditions != null) {
                        return new Criteria(conditions);
                    }
                }
            }

            throw new JsonMappingException(p, "Could not create Criteria");
        }
    }

    private final Conditions conditions;

    public Criteria(Conditions conditions) {
        this.conditions = conditions;
    }

    public Conditions getConditions() {
        return conditions;
    }
}
