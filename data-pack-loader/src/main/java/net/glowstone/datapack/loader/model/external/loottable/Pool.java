package net.glowstone.datapack.loader.model.external.loottable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.loottable.entry.PoolEntry;
import net.glowstone.datapack.loader.model.external.loottable.function.Function;

import java.util.List;

public class Pool {
    private final List<Condition> conditions;
    private final List<Function> functions;
    private final RangedInt rolls;
    private final RangedInt bonusRolls;
    private final List<PoolEntry> entries;

    @JsonCreator
    public Pool(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("functions") List<Function> functions,
        @JsonProperty("rolls") RangedInt rolls,
        @JsonProperty("bonusRolls") RangedInt bonusRolls,
        @JsonProperty("entries") List<PoolEntry> entries) {
        this.conditions = conditions;
        this.functions = functions;
        this.rolls = rolls;
        this.bonusRolls = bonusRolls;
        this.entries = entries;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public RangedInt getRolls() {
        return rolls;
    }

    public RangedInt getBonusRolls() {
        return bonusRolls;
    }

    public List<PoolEntry> getEntries() {
        return entries;
    }
}
