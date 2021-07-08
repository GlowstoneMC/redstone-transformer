package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.loottable.SourceEntity;

import java.util.List;

public class FillPlayerHeadFunction extends Function {
    public static final String TYPE_ID = "minecraft:fill_player_head";

    private final SourceEntity entity;

    @JsonCreator
    public FillPlayerHeadFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("entity") SourceEntity entity) {
        super(conditions);
        this.entity = entity;
    }

    public SourceEntity getEntity() {
        return entity;
    }
}
