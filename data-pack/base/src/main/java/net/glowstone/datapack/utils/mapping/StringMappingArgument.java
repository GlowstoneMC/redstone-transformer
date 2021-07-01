package net.glowstone.datapack.utils.mapping;

public class StringMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.STRING;

    private final String value;

    public StringMappingArgument(String value) {
        super(ARGUMENT_TYPE);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
