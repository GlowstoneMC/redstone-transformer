package net.glowstone.datapack.utils.mapping;

public class ClassReferenceMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.CLASS_REFERENCE;

    private final Class<?> type;

    public ClassReferenceMappingArgument(Class<?> type) {
        super(ARGUMENT_TYPE);
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
