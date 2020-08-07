package net.glowstone.datapack.utils.mapping;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

public class TagInstanceMapping {
    private final String registry;
    private final Class<? extends Keyed> type;
    private final NamespacedKey key;

    public TagInstanceMapping(String registry, NamespacedKey key, Class<? extends Keyed> type) {
        this.registry = registry;
        this.type = type;
        this.key = key;
    }

    public String getRegistry() {
        return registry;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public Class<? extends Keyed> getType() {
        return type;
    }
}
