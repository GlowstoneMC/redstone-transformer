package net.glowstone.block.data.states;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
    public <T, S extends StateValue<T>> T getValue(String propName, Class<S> propType) {
        return getStateValue(propName, propType).getValue();
    }

    @Override
    public <T, S extends StateValue<T>> void setValue(String propName, Class<S> propType, T propValue) {
        getStateValue(propName, propType).setValue(propValue);
    }

    @Override
    public <T, S extends StateValue<T>> Set<T> getValidValues(String propName, Class<S> propType) {
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
    public <T, S extends StateValue<T>> T getMaxValue(String propName, Class<S> propType) {
        return getStateValue(propName, propType).getReport().getMaxValue();
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
    public abstract AbstractStatefulBlockData clone();

    private <S extends StateValue<?>> S getStateValue(String propName, Class<S> stateType) {
        StateValue<?> value = stateValues.get(propName);
        if (!value.getClass().isAssignableFrom(stateType)) {
            throw new IllegalStateException("Value for property '" + value + "' not of state type '" + stateType.getSimpleName() + "'.");
        }
        //noinspection unchecked
        return (S) value;
    }
}
