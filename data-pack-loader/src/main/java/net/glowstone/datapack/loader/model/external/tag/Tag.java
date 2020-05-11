package net.glowstone.datapack.loader.model.external.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Tag {
    private final boolean replace;
    private final List<String> values;

    public Tag(
        @JsonProperty("replace") boolean replace,
        @JsonProperty("values") List<String> values) {
        this.replace = replace;
        this.values = values;
    }
}
