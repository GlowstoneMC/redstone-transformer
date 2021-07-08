package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.Sets;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.inputs.RecipeInput;
import net.glowstone.datapack.recipes.inputs.RecipeInputRegistry;
import net.glowstone.datapack.recipes.providers.RecipeProvider.RecipeProviderFactory;
import net.glowstone.datapack.recipes.providers.StaticRecipeProvider.StaticRecipeProviderFactory;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.truth.OptionalSubject.optionals;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.google.common.truth.Truth8.assertThat;

public class RecipeProviderRegistryTest {
    @Test
    void testAllProvidersAreRegisteredCorrectly() throws Exception {
        Set<RecipeProviderFactory<?, ?, ?>> allFactories = new HashSet<>(
            RecipeProviderRegistryTest.getStaticFieldValue(RecipeProviderRegistry.class, "ALL_FACTORIES")
        );
        Map<Class<? extends Recipe>, StaticRecipeProviderFactory<?, ?, ?, ?>> staticFactories = new HashMap<>(
            RecipeProviderRegistryTest.getStaticFieldValue(RecipeProviderRegistry.class, "STATIC_FACTORIES_BY_MODEL_TYPE")
        );
        Map<Class<? extends Recipe>, SpecialRecipeProvider.SpecialRecipeProviderFactory<?, ?, ?>> specialFactories = new HashMap<>(
            RecipeProviderRegistryTest.getStaticFieldValue(RecipeProviderRegistry.class, "SPECIAL_FACTORIES_BY_MODEL_TYPE")
        );

        assertThat(allFactories).isEqualTo(
            Sets.union(new HashSet<>(staticFactories.values()), new HashSet<>(specialFactories.values()))
        );

        Reflections inputsReflections = new Reflections(RecipeInputRegistry.class.getPackage().getName());
        Set<Class<? extends RecipeInput>> allInputs = inputsReflections.getSubTypesOf(RecipeInput.class)
            .stream()
            .filter((inputClass) -> !Modifier.isAbstract(inputClass.getModifiers()) && !Modifier.isInterface(inputClass.getModifiers()))
            .collect(Collectors.toSet());

        Reflections providersReflections = new Reflections(getClass().getPackage().getName());
        providersReflections.getSubTypesOf(RecipeProvider.class)
            .stream()
            .filter((providerClass) -> !Modifier.isAbstract(providerClass.getModifiers()) && !Modifier.isInterface(providerClass.getModifiers()))
            .forEach((providerClass) -> {
                RecipeProviderFactory<?, ?, ?> factory = getStaticMethodValue(providerClass, "factory", new Class<?>[0], new Object[0]);
                assertWithMessage("Provider type %s has correct factory name", providerClass.getSimpleName())
                    .that(factory.getClass().getSimpleName())
                    .isEqualTo(providerClass.getSimpleName() + "Factory");
                assertWithMessage("Provider type %s in factory registry", providerClass.getSimpleName())
                    .that(allFactories)
                    .contains(factory);

                Class<? extends RecipeInput> inputType = factory.getInputClass();
                assertWithMessage("Provider type %s has correct input name", providerClass.getSimpleName())
                    .that(inputType.getSimpleName())
                    .isEqualTo(providerClass.getSimpleName().replace("Provider", "Input"));
                assertWithMessage("Provider type %s has correctly located input", providerClass.getSimpleName())
                    .that(allInputs)
                    .contains(inputType);

                allFactories.remove(factory);
                allInputs.remove(inputType);
            });

        assertThat(allFactories).isEmpty();
    }

    private static Method getStaticMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T getStaticMethodValue(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] parameterValues) {
        try {
            //noinspection unchecked
            return (T) getStaticMethod(clazz, methodName, parameterTypes).invoke(null, parameterValues);
        } catch (IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException(e);
        }
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

    private static Method getInstanceMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T getInstanceMethodValue(Class<?> clazz, Object obj, String methodName, Class<?>[] parameterTypes, Object[] parameterValues) {
        try {
            //noinspection unchecked
            return (T) getInstanceMethod(clazz, methodName, parameterTypes).invoke(obj, parameterValues);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
