package net.glowstone.datapack.loader.model.external.worldgen.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class BiomeDef {
    public enum Precipitation {
        @JsonProperty("none") NONE,
        @JsonProperty("rain") RAIN,
        @JsonProperty("snow") SNOW,
    }

    public enum Category {
        @JsonProperty("none") NONE,
        @JsonProperty("taiga") TAIGA,
        @JsonProperty("extreme_hills") EXTREME_HILLS,
        @JsonProperty("jungle") JUNGLE,
        @JsonProperty("mesa") MESA,
        @JsonProperty("plains") PLAINS,
        @JsonProperty("savanna") SAVANNA,
        @JsonProperty("icy") ICY,
        @JsonProperty("the_end") THE_END,
        @JsonProperty("beach") BEACH,
        @JsonProperty("forest") FOREST,
        @JsonProperty("ocean") OCEAN,
        @JsonProperty("desert") DESERT,
        @JsonProperty("river") RIVER,
        @JsonProperty("swamp") SWAMP,
        @JsonProperty("mushroom") MUSHROOM,
        @JsonProperty("nether") NETHER,
    }

    public enum TemperatureModifier {
        @JsonProperty("none") NONE,
        @JsonProperty("frozen") FROZEN,
    }

    private final Precipitation precipitation;
    private final Category category;
    private final double temperature;
    private final Optional<TemperatureModifier> temperatureModifier;
    private final double downfall;
    private final Optional<Double> creatureSpawnProbability;

    @JsonCreator
    public BiomeDef(
            @JsonProperty("precipitation") Precipitation precipitation,
            @JsonProperty("category") Category category,
            @JsonProperty("temperature") double temperature,
            @JsonProperty("temperature_modifier") Optional<TemperatureModifier> temperatureModifier,
            @JsonProperty("downfall") double downfall,
            @JsonProperty("creature_spawn_probability") Optional<Double> creatureSpawnProbability
    ) {
        this.precipitation = precipitation;
        this.category = category;
        this.temperature = temperature;
        this.temperatureModifier = temperatureModifier;
        this.downfall = downfall;
        this.creatureSpawnProbability = creatureSpawnProbability;
    }

    public Precipitation getPrecipitation() {
        return precipitation;
    }

    public Category getCategory() {
        return category;
    }

    public double getTemperature() {
        return temperature;
    }

    public Optional<TemperatureModifier> getTemperatureModifier() {
        return temperatureModifier;
    }

    public double getDownfall() {
        return downfall;
    }

    public Optional<Double> getCreatureSpawnProbability() {
        return creatureSpawnProbability;
    }
}
