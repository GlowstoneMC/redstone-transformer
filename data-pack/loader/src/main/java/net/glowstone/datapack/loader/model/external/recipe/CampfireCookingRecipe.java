package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public class CampfireCookingRecipe implements CookingRecipe {
    public static final String TYPE_ID = "minecraft:campfire_cooking";

    private final Optional<String> group;
    private final List<Item> ingredient;
    private final String result;
    private final double experience;
    private final int cookingTime;

    public CampfireCookingRecipe(
        @JsonProperty("group") Optional<String> group,
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
    public Optional<String> getGroup() {
        return group;
    }

    @Override
    public List<Item> getIngredient() {
        return ingredient;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public double getExperience() {
        return experience;
    }

    @Override
    public int getCookingTime() {
        return cookingTime;
    }
}
