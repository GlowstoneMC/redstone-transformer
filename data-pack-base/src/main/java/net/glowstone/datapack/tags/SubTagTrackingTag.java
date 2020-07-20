package net.glowstone.datapack.tags;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.util.Consumer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SubTagTrackingTag<T extends Keyed> implements Tag<T> {
    private final NamespacedKey key;
    private final Multiset<T> allValues;
    private final Set<T> directValues;
    private final Set<SubTagTrackingTag<T>> subTags;
    private final Set<Consumer<SubTagChanges<T>>> superTagEvents;

    @SuppressWarnings("unchecked")
    public SubTagTrackingTag(NamespacedKey key, Set<T> directValues, Set<SubTagTrackingTag<T>> subTags) {
        this.key = key;
        this.allValues = HashMultiset.create();
        this.directValues = new HashSet<>();
        this.subTags = new HashSet<>();
        this.superTagEvents = new HashSet<>();

        this.replaceValues(directValues, subTags);
    }

    @Override
    public boolean isTagged(T item) {
        return this.allValues.contains(item);
    }

    @Override
    public Set<T> getValues() {
        return Collections.unmodifiableSet(this.allValues.elementSet());
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    public void replaceValues(Set<T> directValues,
                              Set<SubTagTrackingTag<T>> subTags) {
        Multiset<T> origAllValues = HashMultiset.create(allValues);
        Set<SubTagTrackingTag<T>> origSubTags = new HashSet<>(subTags);

        this.allValues.clear();
        this.directValues.clear();
        this.subTags.clear();

        this.directValues.addAll(directValues);
        this.allValues.addAll(directValues);
        this.subTags.addAll(subTags);

        Sets.difference(origSubTags, subTags)
            .forEach((removedSubTag) -> {
                removedSubTag.removeChangeListener(this::subTagChangeListener);
            });

        Sets.difference(subTags, origSubTags)
            .forEach((addedSubTag) -> {
                addedSubTag.addChangeListener(this::subTagChangeListener);
                this.allValues.addAll(addedSubTag.getValues());
            });

        SubTagChanges<T> changes = new SubTagChanges<>(
            Sets.difference(this.allValues.elementSet(), origAllValues.elementSet()),
            Sets.difference(origAllValues.elementSet(), this.allValues.elementSet())
        );

        superTagEvents.forEach((event) -> event.accept(changes));
    }

    public void changeValues(Set<T> addedValues,
                             Set<T> removedValues,
                             Set<SubTagTrackingTag<T>> addedSubTags,
                             Set<SubTagTrackingTag<T>> removedSubTags) {
        Set<T> newValues = new HashSet<>();
        Set<T> goneValues = new HashSet<>();

        addedValues.forEach((value) -> {
            if (!this.directValues.contains(value)) {
                this.directValues.add(value);
                this.allValues.add(value);

                if (this.allValues.count(value) == 1) {
                    newValues.add(value);
                }
            }
        });

        removedValues.forEach((value) -> {
            if (this.directValues.contains(value)) {
                if (this.allValues.count(value)== 1) {
                    goneValues.add(value);
                }

                this.directValues.remove(value);
                this.allValues.remove(value);
            }
        });

        addedSubTags.forEach((subTag) -> {
            if (!this.subTags.contains(subTag)) {
                Set<T> subTagValues = subTag.getValues();
                this.subTags.add(subTag);
                subTag.addChangeListener(this::subTagChangeListener);
                this.allValues.addAll(subTagValues);

                subTagValues.forEach((value) -> {
                    if (this.allValues.count(value) == 1) {
                        newValues.add(value);
                    }
                });
            }
        });

        removedSubTags.forEach((subTag) -> {
            if (this.subTags.contains(subTag)) {
                Set<T> subTagValues = subTag.getValues();

                subTagValues.forEach((value) -> {
                    if (this.allValues.count(value) == 1) {
                        goneValues.add(value);
                    }
                });

                this.subTags.remove(subTag);
                subTag.removeChangeListener(this::subTagChangeListener);
                this.allValues.removeAll(subTagValues);

            }
        });

        SubTagChanges<T> changes = new SubTagChanges<>(newValues, goneValues);
        this.superTagEvents.forEach((event) -> event.accept(changes));
    }

    private void addChangeListener(Consumer<SubTagChanges<T>> listener) {
        superTagEvents.add(listener);
    }

    private void removeChangeListener(Consumer<SubTagChanges<T>> listener) {
        superTagEvents.remove(listener);
    }

    private void subTagChangeListener(SubTagChanges<T> subTagChanges) {
        changeValues(subTagChanges.getAddedItems(), subTagChanges.getRemovedItems(), Collections.emptySet(), Collections.emptySet());
    }

    private static class SubTagChanges<T extends Keyed> {
        private final Set<T> addedItems;
        private final Set<T> removedItems;

        private SubTagChanges(Set<T> addedItems, Set<T> removedItems) {
            this.addedItems = addedItems;
            this.removedItems = removedItems;
        }

        public Set<T> getAddedItems() {
            return addedItems;
        }

        public Set<T> getRemovedItems() {
            return removedItems;
        }
    }
}
