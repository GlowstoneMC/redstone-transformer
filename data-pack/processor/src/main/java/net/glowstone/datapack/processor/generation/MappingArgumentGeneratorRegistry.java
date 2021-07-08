package net.glowstone.datapack.processor.generation;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.CodeBlock;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.glowstone.datapack.processor.generation.arguments.AbstractMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.CharacterMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.ClassConstructorMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.ClassReferenceMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.EnumMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.FloatMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.IntegerMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.ListMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.MapMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.MethodReferenceMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.OptionalMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.SetMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.StringMappingArgumentGenerator;
import net.glowstone.datapack.processor.generation.arguments.TagMappingArgumentGenerator;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;

public class MappingArgumentGeneratorRegistry {
    private static final Set<AbstractMappingArgumentGenerator<?>> ALL_GENERATORS =
        ImmutableSet.of(
            CharacterMappingArgumentGenerator.getInstance(),
            ClassConstructorMappingArgumentGenerator.getInstance(),
            ClassReferenceMappingArgumentGenerator.getInstance(),
            EnumMappingArgumentGenerator.getInstance(),
            FloatMappingArgumentGenerator.getInstance(),
            IntegerMappingArgumentGenerator.getInstance(),
            ListMappingArgumentGenerator.getInstance(),
            MapMappingArgumentGenerator.getInstance(),
            MethodReferenceMappingArgumentGenerator.getInstance(),
            OptionalMappingArgumentGenerator.getInstance(),
            SetMappingArgumentGenerator.getInstance(),
            StringMappingArgumentGenerator.getInstance(),
            TagMappingArgumentGenerator.getInstance()
        );

    private static final Map<MappingArgumentType, AbstractMappingArgumentGenerator<?>> GENERATORS_BY_TYPE =
        ALL_GENERATORS
            .stream()
            .collect(Collectors.toMap(
                AbstractMappingArgumentGenerator::getArgumentType,
                Function.identity()
            ));

    public static CodeBlock mapArgument(String tagManagerName, AbstractMappingArgument argument) {
        if (!GENERATORS_BY_TYPE.containsKey(argument.getArgumentType())) {
            throw new IllegalStateException("No supported mapping values found.");
        }

        return GENERATORS_BY_TYPE.get(argument.getArgumentType())
            .map(tagManagerName, argument);
    }
}
