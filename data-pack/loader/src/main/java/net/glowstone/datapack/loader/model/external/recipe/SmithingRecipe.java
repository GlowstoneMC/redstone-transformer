package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public class SmithingRecipe implements GroupableRecipe {
    public static final String TYPE_ID = "minecraft:smithing";

    private final Optional<String> group;
    private final Item base;
    private final Item addition;
    private final Item result;

    public SmithingRecipe(
        @JsonProperty("group") Optional<String> group,
        @JsonProperty("base") Item base,
        @JsonProperty("addition") Item addition,
        @JsonProperty("result") Item result) {
        this.group = group;
        this.base = base;
        this.addition = addition;
        this.result = result;
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
