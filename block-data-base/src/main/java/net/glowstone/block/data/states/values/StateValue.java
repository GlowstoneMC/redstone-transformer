package net.glowstone.block.data.states.values;

import java.util.Objects;
import java.util.Optional;
import net.glowstone.block.data.states.reports.StateReport;

public class StateValue<T> implements Cloneable {
    private Optional<T> value;
    private boolean modified;
    private final StateReport<T> report;

    public StateValue(Optional<T> value, StateReport<T> report) {
        this.value = value.filter((v) -> !v.equals(report.getDefaultValue()));
        this.modified = false;
        this.report = report;
    }

    public String getValueAsString() {
        return report.stringifyValue(getValue());
    }

    public T getValue() {
        return value.orElse(report.getDefaultValue());
    }

    public void setValueFromString(String stringValue) {
        setValue(report.parseValue(stringValue));
    }

    public void setRawValue(Object rawValue) {
        if (!report.getValueType().isAssignableFrom(rawValue.getClass())) {
            throw new IllegalArgumentException("Illegal type for value. Expected: '" + report.getValueType() +"', Found: '" + rawValue.getClass());
        }
        @SuppressWarnings("unchecked") T value = (T) rawValue;
        setValue(value);
    }

    public void setValue(T value) {
        if (this.value.isPresent() && !this.value.get().equals(value)) {
            if (!report.getValidValues().contains(value)) {
                throw new IllegalArgumentException("Invalid value: '" + value + "'");
            }
            this.value = Optional.of(value);
            this.modified = true;
        }
    }

    public boolean hasValue() {
        return value.isPresent();
    }

    public boolean hasBeenModified() {
        return modified;
    }

    public StateReport<T> getReport() {
        return report;
    }

    @Override
    public StateValue<T> clone() {
        return new StateValue<>(value, report);
    }

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
