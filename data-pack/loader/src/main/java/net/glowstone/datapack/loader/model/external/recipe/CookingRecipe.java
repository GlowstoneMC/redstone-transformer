package net.glowstone.datapack.loader.model.external.recipe;

import java.util.List;

public interface CookingRecipe extends GroupableRecipe {
    List<Item> getIngredient();

    String getResult();

    double getExperience();

    int getCookingTime();
}
