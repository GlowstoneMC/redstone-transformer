package net.glowstone.datapack.registry;

public class RegistryEntry<T> {
    private final String name;
    private final int id;
    private final T element;

    public RegistryEntry(String name, int id, T element) {
        this.name = name;
        this.id = id;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public T getElement() {
        return element;
    }

    public void toNbt() {
        // TODO
    }
}
