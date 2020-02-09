package net.glowstone.block.data.states;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.glowstone.block.data.states.values.StateValue;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public abstract class AbstractStatefulBlockData implements StatefulBlockData {
    protected final Material material;
    protected final Map<String, StateValue<?>> stateValues;

    protected AbstractStatefulBlockData(Material material, Map<String, StateValue<?>> stateValues) {
        this.material = material;
        this.stateValues = stateValues;
    }

    @Override
    public <T> T getValue(String propName, Class<T> propType) {
        return getStateValue(propName, propType).getValue();
    }

    @Override
    public <T> void setValue(String propName, Class<T> propType, T propValue) {
        getStateValue(propName, propType).setValue(propValue);
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
        String materialName = "minecraft:" + material.name().toLowerCase();
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
    public <T> T getMaxValue(String propName, Class<T> propType) {
        return getStateValue(propName, propType).getReport().getMaxValue();
    }

    @Override
    public boolean hasValue(String propName) {
        return stateValues.containsKey(propName);
    }

    @Override
    public BlockData merge(BlockData data) {
        AbstractStatefulBlockData newData = this.clone();
        ((StatefulBlockData)data).getSerializedStateProps().forEach((propName, propValue) -> {
            if (newData.stateValues.containsKey(propName)) {
                newData.stateValues.get(propName).setValueFromString(propValue);
            }
        });
        return newData;
    }

    @Override
    public boolean matches(BlockData data) {
        // Note: this is not the correct implementation. Currently, there's no way to determine whether data has been
        // parsed
        return Objects.equals(data, this);
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
}
