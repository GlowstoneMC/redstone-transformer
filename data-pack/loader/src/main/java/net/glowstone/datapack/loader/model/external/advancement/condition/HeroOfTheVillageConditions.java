package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Location;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class HeroOfTheVillageConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:hero_of_the_village";

    private final Optional<Location> location;

    @JsonCreator
    public HeroOfTheVillageConditions(
        @JsonProperty("location") Optional<Location> location,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.location = location;
    }

    public Optional<Location> getLocation() {
        return location;
    }
}
