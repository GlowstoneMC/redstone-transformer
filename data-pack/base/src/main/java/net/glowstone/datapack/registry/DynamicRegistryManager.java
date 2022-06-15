package net.glowstone.datapack.registry;

import java.util.Collections;
import java.util.List;

public class DynamicRegistryManager<T> {
    private final String type;
    private final List<RegistryEntry<T>> value;

    public DynamicRegistryManager(String type, List<RegistryEntry<T>> value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public List<RegistryEntry<T>> getValue() {
        return Collections.unmodifiableList(value);
    }

    public void addElement(String name, T element) {
        value.add(new RegistryEntry<>(name, value.size(), element));
    }

    public void toNbt() {
        // TODO
    }
}
