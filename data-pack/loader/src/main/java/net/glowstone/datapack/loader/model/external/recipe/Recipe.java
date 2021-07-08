package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.glowstone.datapack.loader.model.external.recipe.special.ArmorDyeRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.BannerAddPatternRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.BannerDuplicateRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.BookCloningRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkRocketRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkStarFadeRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.FireworkStarRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.MapCloningRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.MapExtendingRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.RepairItemRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.ShieldDecorationRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.ShulkerBoxColoringRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.SuspiciousStewRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.TippedArrowRecipe;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ArmorDyeRecipe.class, name = ArmorDyeRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = BannerAddPatternRecipe.class, name = BannerAddPatternRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = BannerDuplicateRecipe.class, name = BannerDuplicateRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = BookCloningRecipe.class, name = BookCloningRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = FireworkRocketRecipe.class, name = FireworkRocketRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = FireworkStarRecipe.class, name = FireworkStarRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = FireworkStarFadeRecipe.class, name = FireworkStarFadeRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = MapCloningRecipe.class, name = MapCloningRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = MapExtendingRecipe.class, name = MapExtendingRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = RepairItemRecipe.class, name = RepairItemRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = ShieldDecorationRecipe.class, name = ShieldDecorationRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = ShulkerBoxColoringRecipe.class, name = ShulkerBoxColoringRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = TippedArrowRecipe.class, name = TippedArrowRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = SuspiciousStewRecipe.class, name = SuspiciousStewRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = ShapedRecipe.class, name = ShapedRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = ShapelessRecipe.class, name = ShapelessRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = SmeltingRecipe.class, name = SmeltingRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = SmithingRecipe.class, name = SmithingRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = SmokingRecipe.class, name = SmokingRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = StonecuttingRecipe.class, name = StonecuttingRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = BlastingRecipe.class, name = BlastingRecipe.TYPE_ID),
    @JsonSubTypes.Type(value = CampfireCookingRecipe.class, name = CampfireCookingRecipe.TYPE_ID),
})
public interface Recipe {
}
