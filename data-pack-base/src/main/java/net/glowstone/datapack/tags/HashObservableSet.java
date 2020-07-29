package net.glowstone.datapack.tags;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HashObservableSet<E> implements ObservableSet<E> {
    private static <E> boolean anySucceeded(Collection<E> c, Function<E, Boolean> p) {
        return c.stream()
            .map(p)
            .reduce((a, b) -> a && b)
            .orElse(false);
    }

    private final Multiset<E> allValues;
    private final Set<E> directValues;
    private final Set<ObservableSet<? extends E>> subSets;
    private final Set<BiConsumer<Set<E>, Set<E>>> superSetListeners;
    private final Class<E> valueClass;

    public HashObservableSet(Class<E> valueClass) {
        this.allValues = HashMultiset.create();
        this.directValues = new HashSet<>();
        this.subSets = new HashSet<>();
        this.superSetListeners = new HashSet<>();
        this.valueClass = valueClass;
    }

    public HashObservableSet(Class<E> valueClass, Set<? extends E> directValues, Set<? extends ObservableSet<? extends E>> subSets) {
        this(valueClass);
        this.addAll(directValues, subSets);
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public HashObservableSet(HashObservableSet<E> other) {
        this(other.valueClass, other.directValues, other.subSets);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashObservableSet<?> that = (HashObservableSet<?>) o;
        return this.allValues.equals(that.allValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.allValues);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public HashObservableSet<E> clone() {
        return new HashObservableSet<>(this);
    }

    @Override
    public Class<E> getValueClass() {
        return valueClass;
    }

    @Override
    public int size() {
        return this.allValues.elementSet().size();
    }

    @Override
    public boolean isEmpty() {
        return this.allValues.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.allValues.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return this.allValues.elementSet().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.allValues.elementSet().toArray();
    }

    @Override
    @SuppressWarnings("SuspiciousToArrayCall")
    public <E> E[] toArray(E[] a) {
        return this.allValues.elementSet().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return this.changeValues(Collections.singleton(e), Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
    }

    @Override
    public boolean add(ObservableSet<? extends E> subSet) {
        return this.changeValues(Collections.emptySet(), Collections.emptySet(), Collections.singleton(subSet), Collections.emptySet());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        if (!valueClass.isAssignableFrom(o.getClass())) {
            return false;
        }
        return this.changeValues(Collections.emptySet(), Collections.singleton((E)o), Collections.emptySet(), Collections.emptySet());
    }
    
    @Override
    public boolean remove(ObservableSet<? extends E> subSet) {
        return this.changeValues(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.singleton(subSet));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.allValues.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> values) {
        return this.changeValues(values, Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
    }

    @Override
    public boolean addAll(Collection<? extends E> values, Collection<? extends ObservableSet<? extends E>> subSets) {
        return this.changeValues(values, Collections.emptySet(), subSets, Collections.emptySet());
    }

    @Override
    public boolean retainAll(Collection<?> values) {
        return this.retainAll(values, Optional.empty());
    }

    @Override
    public boolean retainAll(Collection<?> values, Collection<? extends ObservableSet<? extends E>> subSets) {
        return this.retainAll(values, Optional.of(subSets));
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.removeAll(c, Collections.emptySet());
    }

    @Override
    public boolean removeAll(Collection<?> values, Collection<? extends ObservableSet<? extends E>> subSets) {
        @SuppressWarnings("unchecked")
        Set<E> castedValues = values.stream()
            .filter((o) -> valueClass.isAssignableFrom(o.getClass()))
            .map((o) -> (E)o)
            .collect(Collectors.toSet());

        return this.changeValues(Collections.emptySet(), castedValues, Collections.emptySet(), subSets);
    }

    @Override
    public void clear() {
        Set<E> oldValues = new HashSet<>(this.allValues.elementSet());

        this.subSets.forEach((subSet) -> subSet.removeChangeListener(this::subSetChangeListener));

        this.allValues.clear();
        this.subSets.clear();
        this.directValues.clear();

        this.promoteChanges(Collections.emptySet(), oldValues);
    }

    @Override
    public boolean changeValues(Collection<? extends E> addedValues,
                                Collection<? extends E> removedValues,
                                Collection<? extends ObservableSet<? extends E>> addedSubSets,
                                Collection<? extends ObservableSet<? extends E>> removedSubSets) {
        Set<E> newValues = new HashSet<>();
        Set<E> goneValues = new HashSet<>();

        boolean directAdded = anySucceeded(
            addedValues,
            (value) -> {
                if (!this.directValues.contains(value)) {
                    this.directValues.add(value);
                    this.allValues.add(value);

                    if (this.allValues.count(value) == 1) {
                        newValues.add(value);
                    }

                    return true;
                }
                return false;
            }
        );

        boolean directRemoved = anySucceeded(
            removedValues,
            (value) -> {
                if (this.directValues.contains(value)) {
                    if (this.allValues.count(value)== 1) {
                        goneValues.add(value);
                    }

                    this.directValues.remove(value);
                    this.allValues.remove(value);

                    return true;
                }

                return false;
            }
        );

        boolean subSetsAdded = anySucceeded(
            addedSubSets,
            (subSet) -> {
                if (!this.subSets.contains(subSet)) {
                    this.subSets.add(subSet);
                    subSet.addChangeListener(this::subSetChangeListener);
                    this.allValues.addAll(subSet);

                    subSet.forEach((value) -> {
                        if (this.allValues.count(value) == 1) {
                            newValues.add(value);
                        }
                    });

                    return true;
                }

                return false;
            }
        );

        boolean subSetsRemoved = anySucceeded(
            removedSubSets,
            (subSet) -> {
                if (this.subSets.contains(subSet)) {
                    subSet.forEach((value) -> {
                        if (this.allValues.count(value) == 1) {
                            goneValues.add(value);
                        }
                    });

                    this.subSets.remove(subSet);
                    subSet.removeChangeListener(this::subSetChangeListener);
                    this.allValues.removeAll(subSet);

                    return true;
                }

                return false;
            }
        );

        this.promoteChanges(newValues, goneValues);

        return directAdded || directRemoved || subSetsAdded || subSetsRemoved;
    }

    @Override
    public boolean replaceValues(Optional<Set<? extends E>> directValues,
                                 Optional<Collection<? extends ObservableSet<? extends E>>> subSets) {
        Multiset<E> oldAllValues = HashMultiset.create(allValues);

        directValues.ifPresent((values) -> {
            Set<E> oldValues = new HashSet<>(this.directValues);

            this.directValues.clear();
            this.directValues.addAll(values);

            Multisets.removeOccurrences(this.allValues, oldValues);
            this.allValues.addAll(values);
        });

        subSets.ifPresent((sets) -> {
            Set<ObservableSet<? extends E>> oldSubSets = new HashSet<>(this.subSets);

            this.subSets.clear();
            this.subSets.addAll(sets);

            Sets.difference(oldSubSets, this.subSets)
                .forEach((removedSubSet) -> {
                    Multisets.removeOccurrences(this.allValues, removedSubSet);
                    removedSubSet.removeChangeListener(this::subSetChangeListener);
                });
            Sets.difference(this.subSets, oldSubSets)
                .forEach((addedSubSet) -> {
                    this.allValues.addAll(addedSubSet);
                    addedSubSet.addChangeListener(this::subSetChangeListener);
                });
        });

        this.promoteChanges(
            Sets.difference(this.allValues.elementSet(), oldAllValues.elementSet()),
            Sets.difference(oldAllValues.elementSet(), this.allValues.elementSet())
        );

        return !this.allValues.equals(oldAllValues);
    }

    @Override
    public void addChangeListener(BiConsumer<Set<E>, Set<E>> listener) {
        this.superSetListeners.add(listener);
    }

    @Override
    public void removeChangeListener(BiConsumer<Set<E>, Set<E>> listener) {
        this.superSetListeners.remove(listener);
    }

    private boolean retainAll(Collection<?> values, Optional<Collection<? extends ObservableSet<? extends E>>> subSets) {
        @SuppressWarnings("unchecked")
        Set<E> castedValues = values.stream()
            .filter((o) -> valueClass.isAssignableFrom(o.getClass()))
            .map((o) -> (E)o)
            .collect(Collectors.toSet());

        return this.replaceValues(Optional.of(castedValues), subSets);
    }

    private void promoteChanges(Set<E> newValues, Set<E> goneValues) {
        if (!newValues.isEmpty() || !goneValues.isEmpty()) {
            this.superSetListeners.forEach((listener) -> listener.accept(newValues, goneValues));
        }
    }

    private void subSetChangeListener(Set<? extends E> addedValues, Set<? extends E> removedValues) {
        Set<E> newValues = new HashSet<>();
        Set<E> goneValues = new HashSet<>();

        addedValues.forEach((value) -> {
            this.allValues.add(value);

            if (this.allValues.count(value) == 1) {
                newValues.add(value);
            }
        });

        removedValues.forEach((value) -> {
                if (this.allValues.count(value) == 1) {
                    goneValues.add(value);
                }

                this.allValues.remove(value);
        });

        this.promoteChanges(newValues, goneValues);
    }
}
