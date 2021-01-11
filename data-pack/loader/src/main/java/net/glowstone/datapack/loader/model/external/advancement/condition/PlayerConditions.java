package net.glowstone.datapack.loader.model.external.advancement.condition;

import net.glowstone.datapack.loader.model.external.predicate.Predicate;

import java.util.List;
import java.util.Optional;

public abstract class PlayerConditions implements Conditions {
    private final Optional<List<Predicate>> player;

    protected PlayerConditions(Optional<List<Predicate>> player) {
        this.player = player;
    }

    public Optional<List<Predicate>> getPlayer() {
        return player;
    }
}
