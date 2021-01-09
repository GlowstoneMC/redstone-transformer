package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;

import java.util.List;
import java.util.Optional;

public class EnchantRandomlyFunction extends Function {
    public static final String TYPE_ID = "minecraft:enchant_randomly";

    private final Optional<List<String>> enchantments;

    @JsonCreator
    public EnchantRandomlyFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("enchantments") Optional<List<String>> enchantments) {
        super(conditions);
        this.enchantments = enchantments;
    }

    public Optional<List<String>> getEnchantments() {
        return enchantments;
    }
}
