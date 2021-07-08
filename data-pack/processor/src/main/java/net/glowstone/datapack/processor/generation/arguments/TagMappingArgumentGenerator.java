package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;
import net.glowstone.datapack.utils.mapping.TagMappingArgument;

public class TagMappingArgumentGenerator extends AbstractMappingArgumentGenerator<TagMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.TAG;

    private static final TagMappingArgumentGenerator INSTANCE = new TagMappingArgumentGenerator();

    public static TagMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private TagMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, TagMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, TagMappingArgument argument) {
        return CodeBlock.of(
            "$L.getTag($S, $S, $S, $T.class)",
            tagManagerName,
            argument.getRegistry(),
            argument.getKey().getNamespace(),
            argument.getKey().getKey(),
            argument.getType()
        );
    }
}
