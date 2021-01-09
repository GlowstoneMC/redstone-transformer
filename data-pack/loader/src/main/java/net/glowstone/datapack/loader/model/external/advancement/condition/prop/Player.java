package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Player {
    private final Optional<Map<String, Advancement>> advancements;
    private final Optional<String> gamemode;
    private final Optional<RangedInt> level;
    private final Optional<Map<String, Boolean>> recipes;
    private final Optional<List<Stats>> stats;

    @JsonCreator
    public Player(
        @JsonProperty("advancements") Optional<Map<String, Advancement>> advancements,
        @JsonProperty("gamemode") Optional<String> gamemode,
        @JsonProperty("level") Optional<RangedInt> level,
        @JsonProperty("recipes") Optional<Map<String, Boolean>> recipes,
        @JsonProperty("stats") Optional<List<Stats>> stats) {
        this.advancements = advancements;
        this.gamemode = gamemode;
        this.level = level;
        this.recipes = recipes;
        this.stats = stats;
    }

    public Optional<Map<String, Advancement>> getAdvancements() {
        return advancements;
    }

    public Optional<String> getGamemode() {
        return gamemode;
    }

    public Optional<RangedInt> getLevel() {
        return level;
    }

    public Optional<Map<String, Boolean>> getRecipes() {
        return recipes;
    }

    public Optional<List<Stats>> getStats() {
        return stats;
    }
}
