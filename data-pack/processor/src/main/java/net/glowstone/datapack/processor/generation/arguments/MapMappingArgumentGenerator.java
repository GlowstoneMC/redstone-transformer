package net.glowstone.datapack.processor.generation.arguments;

import com.google.common.collect.ImmutableMap;
import com.squareup.javapoet.CodeBlock;
import java.util.Collections;
import net.glowstone.datapack.processor.generation.MappingArgumentGeneratorRegistry;
import net.glowstone.datapack.utils.mapping.MapMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public class MapMappingArgumentGenerator extends AbstractMappingArgumentGenerator<MapMappingArgument> {
    private static final MappingArgumentType ARGUMENT_TYPE = MappingArgumentType.MAP;

    private static final MapMappingArgumentGenerator INSTANCE = new MapMappingArgumentGenerator();

    public static MapMappingArgumentGenerator getInstance() {
        return INSTANCE;
    }

    private MapMappingArgumentGenerator() {
        super(ARGUMENT_TYPE, MapMappingArgument.class);
    }

    @Override
    public CodeBlock mapImpl(String tagManagerName, MapMappingArgument argument) {
        if (argument.getMap().isEmpty()) {
            return CodeBlock.of(
                "$T.emptyMap()",
                Collections.class
            );
        }
        CodeBlock map = argument.getMap()
            .entrySet()
            .stream()
            .map((entry) -> CodeBlock.of(
                ".put($L, $L)",
                MappingArgumentGeneratorRegistry.mapArgument(tagManagerName, entry.getKey()),
                MappingArgumentGeneratorRegistry.mapArgument(tagManagerName, entry.getValue())
            ))
            .collect(CodeBlock.joining(""));

        return CodeBlock.of(
            "$T.<$T, $T>builder()$L.build()",
            ImmutableMap.class,
            argument.getKeyType(),
            argument.getValueType(),
            map
        );
    }
}
