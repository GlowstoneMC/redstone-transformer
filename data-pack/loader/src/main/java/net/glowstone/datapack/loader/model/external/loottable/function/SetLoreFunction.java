package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.loottable.SourceEntity;
import net.glowstone.datapack.loader.model.external.text.RawJsonText;

import java.util.List;

public class SetLoreFunction extends Function {
    public static final String TYPE_ID = "minecraft:set_lore";

    private final List<RawJsonText> lore;
    private final SourceEntity entity;
    private final boolean replace;

    @JsonCreator
    public SetLoreFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("lore") List<RawJsonText> lore,
        @JsonProperty("entity") SourceEntity entity,
        boolean replace) {
        super(conditions);
        this.lore = lore;
        this.entity = entity;
        this.replace = replace;
    }

    public List<RawJsonText> getLore() {
        return lore;
    }

    public SourceEntity getEntity() {
        return entity;
    }

    public boolean isReplace() {
        return replace;
    }
}
