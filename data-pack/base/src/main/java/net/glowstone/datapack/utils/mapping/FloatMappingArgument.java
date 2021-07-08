package net.glowstone.datapack.utils.mapping;

public class FloatMappingArgument extends AbstractMappingArgument {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.FLOAT;

    private final float value;

    public FloatMappingArgument(float value) {
        super(ARGUMENT_TYPE);
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
