package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Entity;

import java.util.List;
import java.util.Optional;

public class ChanneledLightningConditions implements Conditions {
    public static final String TYPE_ID = "minecraft:channeled_lightning";

    private final Optional<List<Entity>> victims;

    @JsonCreator
    public ChanneledLightningConditions(
        @JsonProperty("victims") Optional<List<Entity>> victims) {
        this.victims = victims;
    }

    public Optional<List<Entity>> getVictims() {
        return victims;
    }
}
