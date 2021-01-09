package net.glowstone.datapack.loader.model.external.advancement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class Rewards {
    private final Optional<List<String>> recipes;
    private final Optional<List<String>> loot;
    private final OptionalInt experience;
    private final Optional<String> function;

    @JsonCreator
    public Rewards(
        @JsonProperty("recipes") Optional<List<String>> recipes,
        @JsonProperty("loot") Optional<List<String>> loot,
        @JsonProperty("experience") OptionalInt experience,
        @JsonProperty("function") Optional<String> function) {
        this.recipes = recipes;
        this.loot = loot;
        this.experience = experience;
        this.function = function;
    }

    public Optional<List<String>> getRecipes() {
        return recipes;
    }

    public Optional<List<String>> getLoot() {
        return loot;
    }

    public OptionalInt getExperience() {
        return experience;
    }

    public Optional<String> getFunction() {
        return function;
    }
}
