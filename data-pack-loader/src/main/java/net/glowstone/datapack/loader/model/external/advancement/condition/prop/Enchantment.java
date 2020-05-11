package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Enchantment {
    private final Optional<String> enchantment;
    private final Optional<RangedInt> levels;

    @JsonCreator
    public Enchantment(
            @JsonProperty("enchantment") Optional<String> enchantment,
            @JsonProperty("levels") Optional<RangedInt> levels) {
        this.enchantment = enchantment;
        this.levels = levels;
    }

    public Optional<String> getEnchantment() {
        return enchantment;
    }

    public Optional<RangedInt> getLevels() {
        return levels;
    }
}
