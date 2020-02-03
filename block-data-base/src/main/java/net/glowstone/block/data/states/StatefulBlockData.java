package net.glowstone.block.data.states;

import java.util.Map;
import java.util.Set;
import org.bukkit.block.data.BlockData;

public interface StatefulBlockData extends BlockData {
    <T, S extends StateValue<T>> T getValue(String propName, Class<S> propType);
    <T, S extends StateValue<T>> void setValue(String propName, Class<S> propType, T propValue);
    <T, S extends StateValue<T>> Set<T> getValidValues(String propName, Class<S> propType);
    <T, S extends StateValue<T>> T getMaxValue(String propName, Class<S> propType);
    Map<String, String> getSerializedStateProps();
}
