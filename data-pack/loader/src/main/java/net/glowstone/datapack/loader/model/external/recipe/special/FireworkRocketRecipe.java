package net.glowstone.datapack.loader.model.external.recipe.special;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class FireworkRocketRecipe implements SpecialRecipe {
    public static final String TYPE_ID = "minecraft:crafting_special_firework_rocket";
    private final Optional<String> category;

    public FireworkRocketRecipe (
            @JsonProperty("category") Optional<String> category
    ) {
        this.category = category;
    }
}
