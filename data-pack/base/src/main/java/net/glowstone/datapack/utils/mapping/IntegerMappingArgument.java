package net.glowstone.datapack.utils.mapping;

public class IntegerMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.INTEGER;

    private final int value;

    public IntegerMappingArgument(int value) {
        super(ARGUMENT_TYPE);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
