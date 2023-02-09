package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;
public class SmithingRecipe implements GroupableRecipe {
    public static final String TYPE_ID = "minecraft:smithing";

    private final Optional<String> group;
    private final Item base;
    private final Item addition;
    private final Item result;
    private final Optional<String> category;

    public SmithingRecipe(
        @JsonProperty("group") Optional<String> group,
        @JsonProperty("base") Item base,
        @JsonProperty("addition") Item addition,
        @JsonProperty("result") Item result,
        @JsonProperty("category") Optional<String> category) {
        this.group = group;
        this.base = base;
        this.addition = addition;
        this.result = result;
        this.category = category;
    }

    @Override
    public Optional<String> getGroup() {
        return group;
    }

    public Item getBase() {
        return base;
    }

    public Item getAddition() {
        return addition;
    }

    public Item getResult() {
        return result;
    }
}
