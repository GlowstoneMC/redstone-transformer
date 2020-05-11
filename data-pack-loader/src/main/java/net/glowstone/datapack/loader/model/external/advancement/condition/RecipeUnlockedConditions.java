package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class RecipeUnlockedConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:recipe_unlocked";

    private final Optional<String> recipe;

    @JsonCreator
    public RecipeUnlockedConditions(
        @JsonProperty("recipe") Optional<String> recipe) {
        this.recipe = recipe;
    }

    public Optional<String> getRecipe() {
        return recipe;
    }
}
