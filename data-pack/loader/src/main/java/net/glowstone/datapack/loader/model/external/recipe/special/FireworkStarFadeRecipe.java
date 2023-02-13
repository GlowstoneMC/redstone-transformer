package net.glowstone.datapack.loader.model.external.recipe.special;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class FireworkStarFadeRecipe implements SpecialRecipe {
    public static final String TYPE_ID = "minecraft:crafting_special_firework_star_fade";
    private final Optional<String> category;

    public FireworkStarFadeRecipe (
            @JsonProperty("category") Optional<String> category
    ) {
        this.category = category;
    }
}
