package net.glowstone.block.data.states;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.glowstone.block.data.states.reports.ComparableStateReport;
import net.glowstone.block.data.states.reports.StateReport;
import net.glowstone.block.data.states.values.StateValue;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public abstract class AbstractStatefulBlockData implements StatefulBlockData {
    protected final Material material;
    protected final Map<String, StateValue<?>> stateValues;
    protected boolean explicit;

    protected AbstractStatefulBlockData(Material material, Map<String, StateValue<?>> stateValues, boolean explicit) {
        this.material = material;
        this.stateValues = stateValues;
        this.explicit = explicit;
    }

    @Override
    public <T> T getValue(String propName, Class<T> propType) {
        return getStateValue(propName, propType).getValue();
    }

    @Override
    public <T> void setValue(String propName, Class<T> propType, T propValue) {
        getStateValue(propName, propType).setValue(propValue);
        this.explicit = false;
    }

    @Override
    public <T> Set<T> getValidValues(String propName, Class<T> propType) {
        return getStateValue(propName, propType).getReport().getValidValues();
    }

    @Override
    public Map<String, String> getSerializedStateProps() {
        Map<String, String> stateProps = stateValues.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getValueAsString()));
        return Collections.unmodifiableMap(stateProps);
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public String getAsString() {
        String materialName = material.getKey().toString();
        if (stateValues.isEmpty()) {
            return materialName;
        } else {
            String joinedProps = stateValues.entrySet().stream()
                .map((e) -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(", ", "[", "]"));
            return materialName + joinedProps;
        }
    }

    @Override
    public String getAsString(boolean hideUnspecified) {
        return hideUnspecified ? "minecraft:" + material.name().toLowerCase() : getAsString();
    }

    @Override
    public <T extends Comparable<T>> T getMinValue(String propName, Class<T> propType) {
        return getComparableReport(propName, propType).getMaxValue();
    }

    @Override
    public <T extends Comparable<T>> T getMaxValue(String propName, Class<T> propType) {
        return getComparableReport(propName, propType).getMaxValue();
    }

    @Override
    public boolean hasValue(String propName) {
        return stateValues.containsKey(propName);
    }

    @Override
    public BlockData merge(BlockData data) {
        AbstractStatefulBlockData newData = this.clone();
        AbstractStatefulBlockData statefulData = (AbstractStatefulBlockData)data;
        if (statefulData.explicit) {
            statefulData.stateValues.forEach((propName, propValue) -> {
                if (propValue.hasValue() && !propValue.hasBeenModified()) {
                    newData.stateValues.put(propName, propValue);
                }
            });
        }
        return newData;
    }

    @Override
    public boolean matches(BlockData data) {
        if(data == null){
            return false;
        }
        AbstractStatefulBlockData statefulBlockData = (AbstractStatefulBlockData)data;
        if (!statefulBlockData.explicit) {
            return this == statefulBlockData;
        }
        Map<String, StateValue<?>> explicitProps = statefulBlockData.stateValues.entrySet().stream()
            .filter((e) -> e.getValue().hasValue() && !e.getValue().hasBeenModified())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return Objects.equals(this.getMaterial(), data.getMaterial()) && explicitProps.entrySet().stream()
            .allMatch((e) -> e.getValue().equals(stateValues.get(e.getKey())));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractStatefulBlockData)) return false;
        AbstractStatefulBlockData that = (AbstractStatefulBlockData) o;
        return material == that.material &&
            stateValues.equals(that.stateValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, stateValues);
    }

    @Override
    public abstract AbstractStatefulBlockData clone();

    private <T> StateValue<T> getStateValue(String propName, Class<T> stateType) {
        StateValue<?> value = stateValues.get(propName);
        if (!value.getReport().getValueType().isAssignableFrom(stateType)) {
            throw new IllegalStateException("Value for property '" + value + "' not of state type '" + stateType.getSimpleName() + "'.");
        }
        //noinspection unchecked
        return (StateValue<T>) value;
    }

    private <T extends Comparable<T>> ComparableStateReport<T> getComparableReport(String propName, Class<T> propType) {
        StateReport<T> stateReport = getStateValue(propName, propType).getReport();
        if (!(stateReport instanceof ComparableStateReport)) {
            throw new IllegalStateException("State report for " + propName + " is not comparable!");
        }
        return (ComparableStateReport<T>) stateReport;
    }
}
