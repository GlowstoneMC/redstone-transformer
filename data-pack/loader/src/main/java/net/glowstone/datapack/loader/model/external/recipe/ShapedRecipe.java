package net.glowstone.datapack.loader.model.external.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShapedRecipe implements GroupableRecipe {
    public static final String TYPE_ID = "minecraft:crafting_shaped";

    private final Optional<String> group;
    private final List<String> pattern;
    private final Map<Character, List<Item>> key;
    private final CraftingResult result;

    public ShapedRecipe(
        @JsonProperty("group") Optional<String> group,
        @JsonProperty("pattern") @JsonFormat(with = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) List<String> pattern,
        @JsonProperty("key") Map<Character, List<Item>> key,
        @JsonProperty("result") CraftingResult result) {
        this.group = group;
        this.pattern = pattern;
        this.key = key;
        this.result = result;
    }

    @Override
    public Optional<String> getGroup() {
        return group;
    }

    public List<String> getPattern() {
        return pattern;
    }

    public Map<Character, List<Item>> getKey() {
        return key;
    }

    public CraftingResult getResult() {
        return result;
    }

}
