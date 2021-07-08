package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class RecipeUnlockedConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:recipe_unlocked";

    private final Optional<String> recipe;

    @JsonCreator
    public RecipeUnlockedConditions(
        @JsonProperty("recipe") Optional<String> recipe,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.recipe = recipe;
    }

    public Optional<String> getRecipe() {
        return recipe;
    }
}
