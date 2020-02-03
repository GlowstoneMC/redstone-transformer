package net.glowstone.block.data.states;

import java.util.Optional;
import java.util.Set;

public abstract class StateReport<T> implements Cloneable {
    private final T defaultValue;
    private final Set<T> validValues;
    private final Optional<T> maxValue;

    protected StateReport(T defaultValue, Set<T> validValues, Optional<T> maxValue) {
        this.defaultValue = defaultValue;
        this.validValues = validValues;
        this.maxValue = maxValue;
    }

    public abstract StateValue<T> createStateValue(Optional<String> value);

    public T getDefaultValue() {
        return defaultValue;
    }

    public Set<T> getValidValues() {
        return validValues;
    }

    public T getMaxValue() {
        return maxValue.orElseThrow(() -> new IllegalStateException("StateValue " + getClass().getSimpleName() + " does not support maxValue"));
    }
}
