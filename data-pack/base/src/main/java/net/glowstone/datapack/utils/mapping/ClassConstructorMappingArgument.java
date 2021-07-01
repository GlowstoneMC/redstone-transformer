package net.glowstone.datapack.utils.mapping;

import java.util.List;

public class ClassConstructorMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.CLASS_CONSTRUCTOR;

    private final Class<?> type;
    private final List<AbstractMappingArgument> arguments;

    public ClassConstructorMappingArgument(Class<?> type, List<AbstractMappingArgument> arguments) {
        super(ARGUMENT_TYPE);
        this.type = type;
        this.arguments = arguments;
    }

    public Class<?> getType() {
        return type;
    }

    public List<AbstractMappingArgument> getArguments() {
        return arguments;
    }
}
