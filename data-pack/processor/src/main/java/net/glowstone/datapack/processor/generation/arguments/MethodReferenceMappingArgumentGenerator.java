package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;
import net.glowstone.datapack.utils.mapping.MethodReferenceMappingArgument;

public class MethodReferenceMappingArgumentGenerator extends AbstractMappingArgumentGenerator<MethodReferenceMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.METHOD_REFERENCE;

    private static final MethodReferenceMappingArgumentGenerator INSTANCE = new MethodReferenceMappingArgumentGenerator();

    public static MethodReferenceMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private MethodReferenceMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, MethodReferenceMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, MethodReferenceMappingArgument argument) {
        return CodeBlock.of(
            "$T::$L",
            argument.getType(),
            argument.getMethod()
        );
    }
}
