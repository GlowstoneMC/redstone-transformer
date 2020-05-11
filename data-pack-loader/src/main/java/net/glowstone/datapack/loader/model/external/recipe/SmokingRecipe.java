package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SmokingRecipe implements GroupableRecipe {
    public static final String TYPE_ID = "minecraft:smoking";

    private final String group;
    private final List<Item> ingredient;
    private final String result;
    private final double experience;
    private final int cookingTime;

    public SmokingRecipe(
        @JsonProperty("group") String group,
        @JsonProperty("ingredient") @JsonFormat(with = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) List<Item> ingredient,
        @JsonProperty("result") String result,
        @JsonProperty("experience") double experience,
        @JsonProperty("cookingtime") int cookingTime) {
        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public List<Item> getIngredient() {
        return ingredient;
    }

    public String getResult() {
        return result;
    }

    public double getExperience() {
        return experience;
    }

    public int getCookingTime() {
        return cookingTime;
    }
}
