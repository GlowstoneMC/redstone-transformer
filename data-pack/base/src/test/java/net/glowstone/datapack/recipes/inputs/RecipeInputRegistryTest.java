package net.glowstone.datapack.recipes.inputs;

import net.glowstone.datapack.recipes.inputs.RecipeInput.RecipeInputFactory;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.truth.OptionalSubject.optionals;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.google.common.truth.Truth8.assertThat;

public class RecipeInputRegistryTest {
    @Test
    void testAllInputsAreRegisteredCorrectly() throws Exception {
        Optional<Field> allFactoriesField = getStaticFieldFromClass(RecipeInputRegistry.class, "ALL_FACTORIES");
        assertThat(allFactoriesField).isPresent();

        Object allFactoriesValue = allFactoriesField.get().get(null);
        assertThat(allFactoriesValue).isInstanceOf(Set.class);
        @SuppressWarnings("unchecked") Set<RecipeInputFactory<?>> allFactories =
            new HashSet<>((Set<RecipeInputFactory<?>>) allFactoriesValue);

        Reflections reflections = new Reflections(getClass().getPackage().getName());
        Set<Class<? extends RecipeInput>> recipeInputs = reflections.getSubTypesOf(RecipeInput.class);
        for (Class<? extends RecipeInput> inputClass : recipeInputs) {
            if (!Modifier.isAbstract(inputClass.getModifiers()) && !Modifier.isInterface(inputClass.getModifiers())) {
                Optional<Method> factoryMethod = getStaticMethodFromClass(inputClass, "factory");
                assertWithMessage("Input type %s", inputClass.getSimpleName())
                    .about(optionals())
                    .that(factoryMethod)
                    .isPresent();
                Object factoryObj = factoryMethod.get().invoke(null);
                assertWithMessage("Input type %s", inputClass.getSimpleName())
                    .that(factoryObj)
                    .isInstanceOf(RecipeInputFactory.class);
                assertWithMessage("Input type %s", inputClass.getSimpleName())
                    .that(factoryObj.getClass().getSimpleName())
                    .isEqualTo(inputClass.getSimpleName() + "Factory");
                assertWithMessage("Input type %s", inputClass.getSimpleName())
                    .that(allFactories)
                    .contains(factoryObj);
                //noinspection SuspiciousMethodCalls
                allFactories.remove(factoryObj);
            }
        }

        assertThat(allFactories).isEmpty();
    }

    private static Optional<Method> getStaticMethodFromClass(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return Optional.of(clazz.getDeclaredMethod(methodName, parameterTypes));
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    private static Optional<Field> getStaticFieldFromClass(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return Optional.of(field);
        } catch (NoSuchFieldException e) {
            return Optional.empty();
        }
    }
}
