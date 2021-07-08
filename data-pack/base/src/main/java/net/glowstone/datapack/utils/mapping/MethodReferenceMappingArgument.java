package net.glowstone.datapack.utils.mapping;

public class MethodReferenceMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.METHOD_REFERENCE;

    private final Class<?> type;
    private final String method;

    public MethodReferenceMappingArgument(Class<?> type, String method) {
        super(ARGUMENT_TYPE);
        this.type = type;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public String getMethod() {
        return method;
    }
}
