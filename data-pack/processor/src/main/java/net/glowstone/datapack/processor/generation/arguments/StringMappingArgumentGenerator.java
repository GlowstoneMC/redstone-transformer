package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;
import net.glowstone.datapack.utils.mapping.StringMappingArgument;

public class StringMappingArgumentGenerator extends AbstractMappingArgumentGenerator<StringMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.STRING;

    private static final StringMappingArgumentGenerator INSTANCE = new StringMappingArgumentGenerator();

    public static StringMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private StringMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, StringMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, StringMappingArgument argument) {
        return CodeBlock.of(
            "$S",
            argument.getValue()
        );
    }
}
