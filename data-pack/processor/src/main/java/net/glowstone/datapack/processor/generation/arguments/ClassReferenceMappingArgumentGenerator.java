package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.utils.mapping.ClassReferenceMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public class ClassReferenceMappingArgumentGenerator extends AbstractMappingArgumentGenerator<ClassReferenceMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.CLASS_REFERENCE;

    private static final ClassReferenceMappingArgumentGenerator INSTANCE = new ClassReferenceMappingArgumentGenerator();

    public static ClassReferenceMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private ClassReferenceMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, ClassReferenceMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, ClassReferenceMappingArgument argument) {
        return CodeBlock.of(
            "$T.class",
            argument.getType()
        );
    }
}
