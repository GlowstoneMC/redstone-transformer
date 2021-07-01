package net.glowstone.datapack.utils.mapping;

public abstract class AbstractMappingArgument {
    private final MappingArgumentType argumentType;

    protected AbstractMappingArgument(MappingArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    public MappingArgumentType getArgumentType() {
        return this.argumentType;
    }
}
