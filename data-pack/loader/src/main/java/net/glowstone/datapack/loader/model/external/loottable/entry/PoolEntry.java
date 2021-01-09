package net.glowstone.datapack.loader.model.external.loottable.entry;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AlternativesPoolEntry.class, name = AlternativesPoolEntry.TYPE_ID),
    @JsonSubTypes.Type(value = DynamicPoolEntry.class, name = DynamicPoolEntry.TYPE_ID),
    @JsonSubTypes.Type(value = EmptyPoolEntry.class, name = EmptyPoolEntry.TYPE_ID),
    @JsonSubTypes.Type(value = GroupPoolEntry.class, name = GroupPoolEntry.TYPE_ID),
    @JsonSubTypes.Type(value = ItemPoolEntry.class, name = ItemPoolEntry.TYPE_ID),
    @JsonSubTypes.Type(value = LootTablePoolEntry.class, name = LootTablePoolEntry.TYPE_ID),
    @JsonSubTypes.Type(value = SequencePoolEntry.class, name = SequencePoolEntry.TYPE_ID),
    @JsonSubTypes.Type(value = TagPoolEntry.class, name = TagPoolEntry.TYPE_ID),
})
public abstract class PoolEntry {
    private final List<Condition> conditions;
    private final int weight;
    private final int quality;

    protected PoolEntry(List<Condition> conditions, int weight, int quality) {
        this.conditions = conditions;
        this.weight = weight;
        this.quality = quality;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public int getWeight() {
        return weight;
    }

    public int getQuality() {
        return quality;
    }
}
