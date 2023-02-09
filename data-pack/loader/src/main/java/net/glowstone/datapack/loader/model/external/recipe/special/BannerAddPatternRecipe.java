package net.glowstone.datapack.loader.model.external.recipe.special;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class BannerAddPatternRecipe implements SpecialRecipe {
    public static final String TYPE_ID = "minecraft:crafting_special_banneraddpattern";
    private final Optional<String> category;

    public BannerAddPatternRecipe (
            @JsonProperty("category") Optional<String> category
    ) {
        this.category = category;
    }
}
