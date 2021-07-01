package net.glowstone.datapack.utils.mapping;

import java.util.Set;

public class SetMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.SET;

    private final Set<AbstractMappingArgument> values;

    public SetMappingArgument(Set<AbstractMappingArgument> values) {
        super(ARGUMENT_TYPE);
        this.values = values;
    }

    public Set<AbstractMappingArgument> getValues() {
        return values;
    }
}
