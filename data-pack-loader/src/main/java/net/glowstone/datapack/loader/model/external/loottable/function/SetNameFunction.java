package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.SourceEntity;
import net.glowstone.datapack.loader.model.external.text.RawJsonText;

public class SetNameFunction {
    public static final String TYPE_ID = "minecraft:set_name";

    private final RawJsonText name;
    private final SourceEntity entity;

    @JsonCreator
    public SetNameFunction(
        @JsonProperty("name") RawJsonText name,
        @JsonProperty("entity") SourceEntity entity) {
        this.name = name;
        this.entity = entity;
    }

    public RawJsonText getName() {
        return name;
    }

    public SourceEntity getEntity() {
        return entity;
    }
}
