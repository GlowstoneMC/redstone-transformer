package net.glowstone.datapack.loader.model.external.advancement.condition.prop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public class Item {
    private final Optional<RangedInt> count;
    private final Optional<RangedInt> durability;
    private final Optional<List<Enchantment>> enchantments;
    private final Optional<List<Enchantment>> storedEnchantments;
    private final Optional<ItemId> item;
    private final Optional<String> nbt;
    private final Optional<String> potion;
    private final Optional<String> tag;

    @JsonCreator
    public Item(
            @JsonProperty("count") Optional<RangedInt> count,
            @JsonProperty("durability") Optional<RangedInt> durability,
            @JsonProperty("enchantments") Optional<List<Enchantment>> enchantments,
            @JsonProperty("stored_enchantments") Optional<List<Enchantment>> storedEnchantments,
            @JsonProperty("item") Optional<ItemId> item,
            @JsonProperty("nbt") Optional<String> nbt,
            @JsonProperty("potion") Optional<String> potion,
            @JsonProperty("tag") Optional<String> tag) {
        this.count = count;
        this.durability = durability;
        this.enchantments = enchantments;
        this.storedEnchantments = storedEnchantments;
        this.item = item;
        this.nbt = nbt;
        this.potion = potion;
        this.tag = tag;
    }

    public Optional<RangedInt> getCount() {
        return count;
    }

    public Optional<RangedInt> getDurability() {
        return durability;
    }

    public Optional<List<Enchantment>> getEnchantments() {
        return enchantments;
    }

    public Optional<List<Enchantment>> getStoredEnchantments() {
        return storedEnchantments;
    }

    public Optional<ItemId> getItem() {
        return item;
    }

    public Optional<String> getNbt() {
        return nbt;
    }

    public Optional<String> getPotion() {
        return potion;
    }

    public Optional<String> getTag() {
        return tag;
    }
}
