package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.utils.mapping.CharacterMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public class CharacterMappingArgumentGenerator extends AbstractMappingArgumentGenerator<CharacterMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.CHARACTER;

    private static final CharacterMappingArgumentGenerator INSTANCE = new CharacterMappingArgumentGenerator();

    public static CharacterMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private CharacterMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, CharacterMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, CharacterMappingArgument argument) {
        return CodeBlock.of(
            "'$L'",
            argument.getValue()
        );
    }
}
