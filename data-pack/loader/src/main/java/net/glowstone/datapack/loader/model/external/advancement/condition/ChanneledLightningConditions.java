package net.glowstone.datapack.loader.model.external.advancement.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public class ChanneledLightningConditions extends PlayerConditions {
    public static final String TYPE_ID = "minecraft:channeled_lightning";

    private final Optional<List<List<Predicate>>> victims;

    @JsonCreator
    public ChanneledLightningConditions(
        @JsonProperty("victims") Optional<List<List<Predicate>>> victims,
        @JsonProperty("player") Optional<List<Predicate>> player) {
        super(player);
        this.victims = victims;
    }

    public Optional<List<List<Predicate>>> getVictims() {
        return victims;
    }
}
