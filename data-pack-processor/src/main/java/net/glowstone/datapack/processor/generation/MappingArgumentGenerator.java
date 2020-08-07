package net.glowstone.datapack.processor.generation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.CodeBlock;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.Optional;

public class MappingArgumentGenerator {
    public static CodeBlock mapArgument(String tagManagerName, MappingArgument argument) {
        if (argument.getStringValue().isPresent()) {
            return CodeBlock.of(
                "$S",
                argument.getStringValue().get()
            );
        }

        if (argument.getCharacterValue().isPresent()) {
            return CodeBlock.of(
                "'$L'",
                argument.getCharacterValue().get()
            );
        }

        if (argument.getIntegerValue().isPresent()) {
            return CodeBlock.of(
                "$L",
                argument.getIntegerValue().get()
            );
        }

        if (argument.getFloatValue().isPresent()) {
            return CodeBlock.of(
                "$Lf",
                argument.getFloatValue().get()
            );
        }

        if (argument.getEnumValue().isPresent()) {
            return CodeBlock.of(
                "$T.$L",
                argument.getEnumValue().get().getClass(),
                argument.getEnumValue().get()
            );
        }

        if (argument.getTagValue().isPresent()) {
            return CodeBlock.of(
                "$L.getTag($S, $S, $S, $T.class)",
                tagManagerName,
                argument.getTagValue().get().getRegistry(),
                argument.getTagValue().get().getKey().getNamespace(),
                argument.getTagValue().get().getKey().getKey(),
                argument.getTagValue().get().getType()
            );
        }

        if (argument.getOptionalValue().isPresent()) {
            if (argument.getOptionalValue().get().isPresent()) {
                return CodeBlock.of(
                    "$T.of($L)",
                    Optional.class,
                    mapArgument(tagManagerName, argument.getOptionalValue().get().get())
                );
            } else {
                return CodeBlock.of(
                    "$T.empty()",
                    Optional.class
                );
            }
        }

        if (argument.getListValue().isPresent()) {
            CodeBlock list = argument.getListValue()
                .get()
                .stream()
                .map((v) -> mapArgument(tagManagerName, v))
                .collect(CodeBlock.joining(", "));

            return CodeBlock.of(
                "$T.of($L)",
                ImmutableList.class,
                list
            );
        }

        if (argument.getSetValue().isPresent()) {
            CodeBlock list = argument.getSetValue()
                .get()
                .stream()
                .map((v) -> mapArgument(tagManagerName, v))
                .collect(CodeBlock.joining(", "));

            return CodeBlock.of(
                "$T.of($L)",
                ImmutableSet.class,
                list
            );
        }

        if (argument.getMapValue().isPresent()) {
            CodeBlock map = argument.getMapValue()
                .get()
                .getArguments()
                .entrySet()
                .stream()
                .map((entry) -> CodeBlock.of(
                    ".put($L, $L)",
                    mapArgument(tagManagerName, entry.getKey()),
                    mapArgument(tagManagerName, entry.getValue())
                ))
                .collect(CodeBlock.joining(""));

            return CodeBlock.of(
                "$T.<$T, $T>builder()$L.build()",
                ImmutableMap.class,
                argument.getMapValue().get().getKeyType(),
                argument.getMapValue().get().getValueType(),
                map
            );
        }

        if (argument.getClassReference().isPresent()) {
            return CodeBlock.of(
                "$T.class",
                argument.getClassReference().get()
            );
        }

        if (argument.getClassConstructor().isPresent()) {
            CodeBlock args = argument.getClassConstructor()
                .get()
                .getArguments()
                .stream()
                .map((v) -> mapArgument(tagManagerName, v))
                .collect(CodeBlock.joining(", "));

            return CodeBlock.of(
                "new $T($L)",
                argument.getClassConstructor().get().getType(),
                args
            );
        }

        if (argument.getMethodReference().isPresent()) {
            return CodeBlock.of(
                "$T::$L",
                argument.getMethodReference().get().getType(),
                argument.getMethodReference().get().getMethod()
            );
        }

        throw new IllegalStateException("No supported mapping values found.");
    }
}
