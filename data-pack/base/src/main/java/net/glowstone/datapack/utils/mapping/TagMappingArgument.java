package net.glowstone.datapack.utils.mapping;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

public class TagMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.TAG;

    private final String registry;
    private final NamespacedKey key;
    private final Class<? extends Keyed> type;

    public TagMappingArgument(String registry, NamespacedKey key1, Class<? extends Keyed> type1) {
        super(ARGUMENT_TYPE);
        this.registry = registry;
        this.key = key1;
        this.type = type1;
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
