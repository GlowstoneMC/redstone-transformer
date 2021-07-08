package net.glowstone.block.data.states.reports;

import java.util.Optional;
import java.util.Set;

public abstract class ComparableStateReport<T extends Comparable<T>> extends StateReport<T> {
    private final T minValue;
    private final T maxValue;

    protected ComparableStateReport(Class<T> valueType, T defaultValue, Set<T> validValues) {
        super(valueType, defaultValue, validValues);

        T minValue = null;
        T maxValue = null;

        for (T value : validValues) {
            if (minValue == null || minValue.compareTo(value) > 0) {
                minValue = value;
            }
            if (maxValue == null || maxValue.compareTo(value) < 0) {
                maxValue = value;
            }
        }

        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }
}
