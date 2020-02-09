package net.glowstone.block.data.states.reports;

import java.util.Optional;
import java.util.Set;
import net.glowstone.block.data.states.values.StateValue;

public abstract class StateReport<T> implements Cloneable {
    private final Class<T> valueType;
    private final T defaultValue;
    private final Set<T> validValues;
    private final Optional<T> maxValue;

    protected StateReport(Class<T> valueType, T defaultValue, Set<T> validValues, Optional<T> maxValue) {
        this.valueType = valueType;
        this.defaultValue = defaultValue;
        this.validValues = validValues;
        this.maxValue = maxValue;
    }

    public abstract String stringifyValue(T value);

    public abstract T parseValue(String value);

    public StateValue<T> createStateValue(Optional<String> value) {
        return new StateValue<>(value.map(this::parseValue), this);
    }

    public Class<T> getValueType() {
        return valueType;
    }

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
