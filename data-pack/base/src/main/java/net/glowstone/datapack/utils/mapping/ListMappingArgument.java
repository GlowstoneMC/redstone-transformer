package net.glowstone.datapack.utils.mapping;

import java.util.List;

public class ListMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.LIST;

    private final List<AbstractMappingArgument> values;

    public ListMappingArgument(List<AbstractMappingArgument> values) {
        super(ARGUMENT_TYPE);
        this.values = values;
    }

    public List<AbstractMappingArgument> getValues() {
        return values;
    }
}
