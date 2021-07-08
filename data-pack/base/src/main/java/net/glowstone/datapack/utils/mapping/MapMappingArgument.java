package net.glowstone.datapack.utils.mapping;

import java.util.Map;

public class MapMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.MAP;

    private final Class<?> keyType;
    private final Class<?> valueType;
    private final Map<AbstractMappingArgument, AbstractMappingArgument> map;

    public MapMappingArgument(Class<?> keyType, Class<?> valueType, Map<AbstractMappingArgument, AbstractMappingArgument> map) {
        super(ARGUMENT_TYPE);
        this.keyType = keyType;
        this.valueType = valueType;
        this.map = map;
    }

    public Class<?> getKeyType() {
        return keyType;
    }

    public Class<?> getValueType() {
        return valueType;
    }

    public Map<AbstractMappingArgument, AbstractMappingArgument> getMap() {
        return map;
    }
}
