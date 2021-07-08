package net.glowstone.datapack.recipes.utils.mapping;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

public class MappingArgumentTest {
    @Test
    void testAllMappingArgumentsMatchEnum() {
        Set<MappingArgumentType> allTypes = Sets.newHashSet(MappingArgumentType.values());

        Reflections mappingReflections = new Reflections(AbstractMappingArgument.class.getPackage().getName());
        Set<Class<? extends AbstractMappingArgument>> allMappings = mappingReflections.getSubTypesOf(AbstractMappingArgument.class)
            .stream()
            .filter((inputClass) -> !Modifier.isAbstract(inputClass.getModifiers()) && !Modifier.isInterface(inputClass.getModifiers()))
            .collect(Collectors.toSet());

        allMappings.forEach((mapping) -> {
            MappingArgumentType argumentType = getStaticFieldValue(mapping, "ARGUMENT_TYPE");

            assertThat(allTypes).contains(argumentType);
            allTypes.remove(argumentType);

            String argumentTypeFormatted = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL).convert(argumentType.name());
            assertThat(mapping.getSimpleName()).isEqualTo(argumentTypeFormatted + "MappingArgument");
        });

        assertThat(allTypes).isEmpty();
    }

    private static Field getStaticField(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T getStaticFieldValue(Class<?> clazz, String fieldName) {
        try {
            //noinspection unchecked
            return (T) getStaticField(clazz, fieldName).get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
