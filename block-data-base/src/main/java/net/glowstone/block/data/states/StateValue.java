package net.glowstone.block.data.states;

import java.util.Objects;
import java.util.Optional;

public abstract class StateValue<T> implements Cloneable {
    protected Optional<T> value;
    protected final StateReport<T> report;

    protected StateValue(Optional<T> value, StateReport<T> report) {
        this.value = value;
        this.report = report;
    }

    public abstract String getValueAsString();

    public T getValue() {
        return value.orElse(report.getDefaultValue());
    }

    public abstract void setValueFromString(String stringValue);

    public void setValue(T value) {
        this.value = Optional.of(value);
    }

    public StateReport<T> getReport() {
        return report;
    }

    @Override
    public abstract StateValue<T> clone();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateValue)) return false;
        StateValue<?> that = (StateValue<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
