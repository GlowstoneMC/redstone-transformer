package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.processor.generation.MappingArgumentGeneratorRegistry;
import net.glowstone.datapack.utils.mapping.ClassConstructorMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public class ClassConstructorMappingArgumentGenerator extends AbstractMappingArgumentGenerator<ClassConstructorMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.CLASS_CONSTRUCTOR;

    private static final ClassConstructorMappingArgumentGenerator INSTANCE = new ClassConstructorMappingArgumentGenerator();

    public static ClassConstructorMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private ClassConstructorMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, ClassConstructorMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, ClassConstructorMappingArgument argument) {
        CodeBlock args = argument.getArguments()
            .stream()
            .map((v) -> MappingArgumentGeneratorRegistry.mapArgument(tagManagerName, v))
            .collect(CodeBlock.joining(", "));

        return CodeBlock.of(
            "new $T($L)",
            argument.getType(),
            args
        );
    }
}
