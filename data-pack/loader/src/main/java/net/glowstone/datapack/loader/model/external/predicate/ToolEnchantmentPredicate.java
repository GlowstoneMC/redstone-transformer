package net.glowstone.datapack.loader.model.external.predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Enchantment;

import java.util.List;

public class ToolEnchantmentPredicate implements Predicate {
    public static final String TYPE_ID = "minecraft:tool_enchantment";

    private final List<Enchantment> enchantments;

    @JsonCreator
    public ToolEnchantmentPredicate(
        @JsonProperty("enchantments") List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }
}
