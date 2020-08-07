package net.glowstone.datapack.tags;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import java.util.Collections;
import java.util.Set;

public class SubTagTrackingTag<T extends Keyed> extends HashObservableSet<T> implements Tag<T> {
    private final NamespacedKey key;

    public SubTagTrackingTag(String namespace, String key, Class<T> valueClass, Set<T> directValues, Set<? extends ObservableSet<? extends T>> subTags) {
        this(new NamespacedKey(namespace, key), valueClass, directValues, subTags);
    }

    public SubTagTrackingTag(NamespacedKey key, Class<T> valueClass, Set<T> directValues, Set<? extends ObservableSet<? extends T>> subTags) {
        super(valueClass, directValues, subTags);

        this.key = key;
    }

    @Override
    public boolean isTagged(T item) {
        return this.contains(item);
    }

    @Override
    public Set<T> getValues() {
        return Collections.unmodifiableSet(this);
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    @Override
    public SubTagTrackingTag<T> clone() {
        return new SubTagTrackingTag<>(key, this.getValueClass(), this.getDirectValues(), this.getSubSets());
    }
}
