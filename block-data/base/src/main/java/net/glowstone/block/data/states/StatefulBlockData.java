package net.glowstone.block.data.states;

import java.util.Map;
import java.util.Set;
import net.glowstone.block.data.states.values.StateValue;
import org.bukkit.block.data.BlockData;

public interface StatefulBlockData extends BlockData {
    <T> T getValue(String propName, Class<T> propType);
    <T> void setValue(String propName, Class<T> propType, T propValue);
    <T> Set<T> getValidValues(String propName, Class<T> propType);
    <T extends Comparable<T>> T getMinValue(String propName, Class<T> propType);
    <T extends Comparable<T>> T getMaxValue(String propName, Class<T> propType);
    boolean hasValue(String propName);
    Map<String, String> getSerializedStateProps();
}
