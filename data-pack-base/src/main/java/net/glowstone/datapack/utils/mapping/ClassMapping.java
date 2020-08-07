package net.glowstone.datapack.utils.mapping;

import java.util.List;

public class ClassMapping {
    private final Class<?> type;
    private final List<MappingArgument> arguments;

    public ClassMapping(Class<?> type, List<MappingArgument> arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    public Class<?> getType() {
        return type;
    }

    public List<MappingArgument> getArguments() {
        return arguments;
    }
}
