package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Item;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class PlayerGeneratesContainerLootConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:player_generates_container_loot";

    private final Optional<String> lootTable;

    @JsonCreator
    public PlayerGeneratesContainerLootConditions(
        @JsonProperty("loot_table") Optional<String> lootTable,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.lootTable = lootTable;
    }

    public Optional<String> getLootTable() {
        return lootTable;
    }
}
