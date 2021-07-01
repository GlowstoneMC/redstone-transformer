package net.glowstone.datapack.processor.generation.arguments;

import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.CodeBlock;
import java.util.Collections;
import net.glowstone.datapack.processor.generation.MappingArgumentGeneratorRegistry;
import net.glowstone.datapack.utils.mapping.ListMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public class ListMappingArgumentGenerator extends AbstractMappingArgumentGenerator<ListMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.LIST;

    private static final ListMappingArgumentGenerator INSTANCE = new ListMappingArgumentGenerator();

    public static ListMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private ListMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, ListMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, ListMappingArgument argument) {
        if (argument.getValues().isEmpty()) {
            return CodeBlock.of(
                "$T.emptyList()",
                Collections.class
            );
        }
        CodeBlock list = argument.getValues()
            .stream()
            .map((v) -> MappingArgumentGeneratorRegistry.mapArgument(tagManagerName, v))
            .collect(CodeBlock.joining(", "));

        return CodeBlock.of(
            "$T.of($L)",
            ImmutableList.class,
            list
        );
    }
}
