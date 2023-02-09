package net.glowstone.datapack.loader.model.external.recipe.special;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class BookCloningRecipe implements SpecialRecipe {
    public static final String TYPE_ID = "minecraft:crafting_special_bookcloning";
    private final Optional<String> category;

    public BookCloningRecipe (
            @JsonProperty("category") Optional<String> category
    ) {
        this.category = category;
    }
}
