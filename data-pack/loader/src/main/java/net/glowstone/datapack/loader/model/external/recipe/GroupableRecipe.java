package net.glowstone.datapack.loader.model.external.recipe;

import java.util.Optional;

public interface GroupableRecipe extends Recipe {
    Optional<String> getGroup();
}
