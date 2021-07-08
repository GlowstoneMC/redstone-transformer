package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.utils.mapping.FloatMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public class FloatMappingArgumentGenerator extends AbstractMappingArgumentGenerator<FloatMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.FLOAT;

    private static final FloatMappingArgumentGenerator INSTANCE = new FloatMappingArgumentGenerator();

    public static FloatMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private FloatMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, FloatMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, FloatMappingArgument argument) {
        return CodeBlock.of(
            "$Lf",
            argument.getValue()
        );
    }
}
