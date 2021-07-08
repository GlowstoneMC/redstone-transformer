package net.glowstone.datapack.utils.mapping;

public class CharacterMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.CHARACTER;

    private final char value;

    public CharacterMappingArgument(char value) {
        super(ARGUMENT_TYPE);
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
