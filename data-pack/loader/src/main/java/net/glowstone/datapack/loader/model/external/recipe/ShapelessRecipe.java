package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public class ShapelessRecipe implements GroupableRecipe {
    public static final String TYPE_ID = "minecraft:crafting_shapeless";

    private final Optional<String> group;
    private final List<List<Item>> ingredients;
    private final CraftingResult result;
    private final Optional<String> category;

    public ShapelessRecipe(
        @JsonProperty("group") Optional<String> group,
        @JsonProperty("ingredients") @JsonFormat(with = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) List<List<Item>> ingredients,
        @JsonProperty("result") CraftingResult result,
        @JsonProperty("category") Optional<String> category) {
        this.group = group;
        this.ingredients = ingredients;
        this.result = result;
        this.category = category;
    }

    public Optional<String> getGroup() {
        return group;
    }

    public List<List<Item>> getIngredients() {
        return ingredients;
    }

    public CraftingResult getResult() {
        return result;
    }
}
