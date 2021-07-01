package net.glowstone.datapack.utils.mapping;

public class EnumMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.ENUM;

    private final Enum<?> value;

    public EnumMappingArgument(Enum<?> value) {
        super(ARGUMENT_TYPE);
        this.value = value;
    }

    public Enum<?> getValue() {
        return value;
    }
}
