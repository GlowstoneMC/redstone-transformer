package net.glowstone.datapack.loader.model.external.loottable.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.Enchantment;

import java.util.List;

public class ToolEnchantmentCondition implements Condition {
    public static final String TYPE_ID = "minecraft:tool_enchantment";

    private final List<Enchantment> enchantments;

    @JsonCreator
    public ToolEnchantmentCondition(
        @JsonProperty("enchantments") List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }
}
