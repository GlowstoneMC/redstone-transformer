package net.glowstone.datapack.processor.generation.arguments;

import com.squareup.javapoet.CodeBlock;
import java.util.Objects;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public abstract class AbstractMappingArgumentGenerator<T extends AbstractMappingArgument> {
    private final MappingArgumentType argumentType;
    private final Class<T> mappingClass;

    protected AbstractMappingArgumentGenerator(MappingArgumentType argumentType, Class<T> mappingClass) {
        this.argumentType = argumentType;
        this.mappingClass = mappingClass;
    }

    public MappingArgumentType getArgumentType() {
        return this.argumentType;
    }

    public Class<? extends AbstractMappingArgument> getMappingClass() {
        return mappingClass;
    }

    @SuppressWarnings("unchecked")
    public CodeBlock map(String tagManagerName, AbstractMappingArgument argument) {
        if (!mappingClass.isAssignableFrom(argument.getClass())) {
            throw new IllegalArgumentException("Argument must be of type " + mappingClass + ", not " + argument.getClass());
        }

        return this.mapImpl(tagManagerName, (T)argument);
    }

    protected abstract CodeBlock mapImpl(String tagManagerName, T argument);

    @Override
    public boolean equals(Object o) {
        return this == o || (o != null && getClass() == o.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
