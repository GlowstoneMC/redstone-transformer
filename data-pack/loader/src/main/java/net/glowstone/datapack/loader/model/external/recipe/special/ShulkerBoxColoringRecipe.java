package net.glowstone.datapack.loader.model.external.recipe.special;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class ShulkerBoxColoringRecipe implements SpecialRecipe {
    public static final String TYPE_ID = "minecraft:crafting_special_shulkerboxcoloring";
    private final Optional<String> category;

    public ShulkerBoxColoringRecipe (
            @JsonProperty("category") Optional<String> category
    ) {
        this.category = category;
    }
}
