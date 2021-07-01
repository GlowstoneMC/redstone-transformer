package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.utils.mapping.IntegerMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public class IntegerMappingArgumentGenerator extends AbstractMappingArgumentGenerator<IntegerMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.INTEGER;

    private static final IntegerMappingArgumentGenerator INSTANCE = new IntegerMappingArgumentGenerator();

    public static IntegerMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private IntegerMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, IntegerMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, IntegerMappingArgument argument) {
        return CodeBlock.of(
            "$L",
            argument.getValue()
        );
    }
}
