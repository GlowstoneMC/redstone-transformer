package net.glowstone.datapack.tags;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExpandableTag<T extends Keyed> implements Tag<T> {
    private final NamespacedKey key;
    private final Set<T> tagValues;

    @SuppressWarnings("unchecked")
    public ExpandableTag(NamespacedKey key, Set<? extends Keyed> tagValues) {
        this.key = key;
        this.tagValues = tagValues.stream()
            .flatMap((value) -> {
                if (value instanceof ExpandableTag) {
                    return ((ExpandableTag<T>) value).getValues().stream();
                } else {
                    return Stream.of((T) value);
                }
            })
            .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public boolean isTagged(T item) {
        return getValues().contains(item);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<T> getValues() {
        return tagValues;
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }
}
