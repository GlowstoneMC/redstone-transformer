package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.utils.mapping.EnumMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public class EnumMappingArgumentGenerator extends AbstractMappingArgumentGenerator<EnumMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.ENUM;

    private static final EnumMappingArgumentGenerator INSTANCE = new EnumMappingArgumentGenerator();

    public static EnumMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private EnumMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, EnumMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, EnumMappingArgument argument) {
        return CodeBlock.of(
            "$T.$L",
            argument.getValue().getClass(),
            argument.getValue()
        );
    }
}
