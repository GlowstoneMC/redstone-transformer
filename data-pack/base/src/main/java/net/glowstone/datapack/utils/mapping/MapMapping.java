package net.glowstone.datapack.utils.mapping;

import java.util.Map;

public class MapMapping {
    private final Class<?> keyType;
    private final Class<?> valueType;
    private final Map<MappingArgument, MappingArgument> arguments;

    public MapMapping(Class<?> keyType, Class<?> valueType, Map<MappingArgument, MappingArgument> arguments) {
        this.keyType = keyType;
        this.valueType = valueType;
        this.arguments = arguments;
    }

    public Class<?> getKeyType() {
        return keyType;
    }

    public Class<?> getValueType() {
        return valueType;
    }

    public Map<MappingArgument, MappingArgument> getArguments() {
        return arguments;
    }
}
