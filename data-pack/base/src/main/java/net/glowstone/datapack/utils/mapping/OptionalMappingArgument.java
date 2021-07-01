package net.glowstone.datapack.utils.mapping;

import java.util.Optional;

public class OptionalMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.OPTIONAL;

    private final Optional<AbstractMappingArgument> value;

    public OptionalMappingArgument(Optional<AbstractMappingArgument> value) {
        super(ARGUMENT_TYPE);
        this.value = value;
    }

    public Optional<AbstractMappingArgument> getValue() {
        return value;
    }
}
