package net.glowstone.datapack.processor.generation.arguments;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.CodeBlock;
import java.util.Collections;
import net.glowstone.datapack.processor.generation.MappingArgumentGeneratorRegistry;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;
import net.glowstone.datapack.utils.mapping.SetMappingArgument;

public class SetMappingArgumentGenerator extends AbstractMappingArgumentGenerator<SetMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.SET;

    private static final SetMappingArgumentGenerator INSTANCE = new SetMappingArgumentGenerator();

    public static SetMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private SetMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, SetMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, SetMappingArgument argument) {
        if (argument.getValues().isEmpty()) {
            return CodeBlock.of(
                "$T.emptySet()",
                Collections.class
            );
        }
        CodeBlock list = argument.getValues()
            .stream()
            .map((v) -> MappingArgumentGeneratorRegistry.mapArgument(tagManagerName, v))
            .collect(CodeBlock.joining(", "));

        return CodeBlock.of(
            "$T.of($L)",
            ImmutableSet.class,
            list
        );
    }
}
