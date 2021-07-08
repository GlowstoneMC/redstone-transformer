package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import java.util.Optional;
import net.glowstone.datapack.processor.generation.MappingArgumentGeneratorRegistry;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;
import net.glowstone.datapack.utils.mapping.OptionalMappingArgument;

public class OptionalMappingArgumentGenerator extends AbstractMappingArgumentGenerator<OptionalMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.OPTIONAL;

    private static final OptionalMappingArgumentGenerator INSTANCE = new OptionalMappingArgumentGenerator();

    public static OptionalMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private OptionalMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, OptionalMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, OptionalMappingArgument argument) {
        return argument.getValue()
            .map((v) -> CodeBlock.of(
                "$T.of($L)",
                Optional.class,
                MappingArgumentGeneratorRegistry.mapArgument(tagManagerName, v)
            ))
            .orElseGet(() -> CodeBlock.of(
                "$T.empty()",
                Optional.class
            ));
    }
}
