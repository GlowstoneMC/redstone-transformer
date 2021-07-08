package net.glowstone.datapack.processor.generation.arguments;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Set;
import java.util.stream.Collectors;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.MappingArgumentType;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

public class MappingArgumentGeneratorTest {
    @Test
    void testAllGeneratorsMatchEnumAndArgumentClass() {
        Set<MappingArgumentType> allTypes = Sets.newHashSet(MappingArgumentType.values());

        Reflections mappingReflections = new Reflections(AbstractMappingArgument.class.getPackage().getName());
        Set<Class<? extends AbstractMappingArgument>> allMappings = mappingReflections.getSubTypesOf(AbstractMappingArgument.class)
            .stream()
            .filter((inputClass) -> !Modifier.isAbstract(inputClass.getModifiers()) && !Modifier.isInterface(inputClass.getModifiers()))
            .collect(Collectors.toSet());

        Reflections generatorReflections = new Reflections(AbstractMappingArgumentGenerator.class.getPackage().getName());
        Set<Class<? extends AbstractMappingArgumentGenerator>> allGenerators = generatorReflections.getSubTypesOf(AbstractMappingArgumentGenerator.class)
            .stream()
            .filter((inputClass) -> !Modifier.isAbstract(inputClass.getModifiers()) && !Modifier.isInterface(inputClass.getModifiers()))
            .collect(Collectors.toSet());

        allGenerators.forEach((generator) -> {
            MappingArgumentType argumentType = getStaticFieldValue(generator, "ARGUMENT_TYPE");

            assertThat(allTypes).contains(argumentType);
            allTypes.remove(argumentType);

            String argumentTypeFormatted = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL).convert(argumentType.name());
            assertThat(generator.getSimpleName()).isEqualTo(argumentTypeFormatted + "MappingArgumentGenerator");

            Class<?> mappingType = (Class<?>)((ParameterizedType)generator.getGenericSuperclass()).getActualTypeArguments()[0];
            assertThat(allMappings).contains(mappingType);
            allMappings.remove(mappingType);

            assertThat(generator.getSimpleName()).isEqualTo(mappingType.getSimpleName() + "Generator");
        });

        assertThat(allTypes).isEmpty();
        assertThat(allMappings).isEmpty();
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
