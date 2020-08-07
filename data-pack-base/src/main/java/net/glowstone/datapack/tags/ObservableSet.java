package net.glowstone.datapack.tags;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

public interface ObservableSet<E> extends Set<E>, Cloneable {
    Class<E> getValueClass();

    Set<E> getDirectValues();

    Set<? extends ObservableSet<? extends E>> getSubSets();

    boolean add(ObservableSet<? extends E> subSet);

    boolean remove(ObservableSet<? extends E> subSet);

    boolean addAll(Collection<? extends E> values, Collection<? extends ObservableSet<? extends E>> subSets);

    boolean retainAll(Collection<?> values, Collection<? extends ObservableSet<? extends E>> subSets);

    boolean removeAll(Collection<?> values, Collection<? extends ObservableSet<? extends E>> subSets);

    boolean changeValues(Collection<? extends E> addedValues,
                         Collection<? extends E> removedValues,
                         Collection<? extends ObservableSet<? extends E>> addedSubSets,
                         Collection<? extends ObservableSet<? extends E>> removedSubSets);

    boolean replaceValues(Optional<Set<? extends E>> directValues,
                          Optional<Collection<? extends ObservableSet<? extends E>>> subSets);

    void addChangeListener(BiConsumer<Set<E>, Set<E>> listener);

    void removeChangeListener(BiConsumer<Set<E>, Set<E>> listener);

    ObservableSet<E> clone();
}
