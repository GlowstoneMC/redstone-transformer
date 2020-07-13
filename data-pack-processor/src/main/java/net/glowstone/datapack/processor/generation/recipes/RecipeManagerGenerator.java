package net.glowstone.datapack.processor.generation.recipes;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import net.glowstone.datapack.AbstractRecipeManager;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.processor.generation.CodeBlockStatementCollector;
import net.glowstone.datapack.processor.generation.DataPackItemSourceGenerator;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecipeManagerGenerator implements DataPackItemSourceGenerator {
    private static final Map<Class<? extends Recipe>, RecipeGenerator<?>> RECIPE_GENERATORS =
        Stream.<RecipeGenerator<?>>of(
            new ShapelessRecipeGenerator()
        )
        .collect(
            Collectors.toMap(
                RecipeGenerator::getAssociatedClass,
                Function.identity()
            )
        );

    private final Map<String, List<MethodSpec>> recipeMethods = new HashMap<>();

    @Override
    public void addItems(Map<String, Map<String, List<String>>> namespacedTaggedItems,
                         String namespaceName,
                         Data data) {
        data.getRecipes().forEach((itemName, recipe) -> {
            RecipeGenerator<?> generator = RECIPE_GENERATORS.get(recipe.getClass());

            if (generator != null) {
                recipeMethods
                    .computeIfAbsent(generator.getDefaultMethodName(), (key) -> new ArrayList<>())
                    .add(
                        generator.generateMethod(namespaceName, itemName, namespacedTaggedItems, recipe)
                    );
            }
        });
    }

    @Override
    public void generateManager(Path generatedClassPath,
                                String generatedClassNamespace) {
        List<MethodSpec> defaultMethods = recipeMethods.entrySet()
            .stream()
            .map((entry) -> MethodSpec.methodBuilder(entry.getKey())
                .addModifiers(Modifier.PRIVATE)
                .returns(ParameterizedTypeName.get(List.class, org.bukkit.inventory.ShapelessRecipe.class))
                .addStatement("$T<$T> recipes = new $T<>()", List.class, org.bukkit.inventory.ShapelessRecipe.class, ArrayList.class)
                .addCode(
                    entry.getValue()
                        .stream()
                        .map((method) -> CodeBlock.of("recipes.addAll($N())", method))
                        .collect(CodeBlockStatementCollector.collect())
                )
                .addStatement("return recipes")
                .build())
            .collect(Collectors.toList());

        MethodSpec defaultRecipesMethod = MethodSpec.methodBuilder("defaultRecipes")
            .addModifiers(Modifier.PROTECTED)
            .returns(ParameterizedTypeName.get(List.class, org.bukkit.inventory.Recipe.class))
            .addStatement("$T<$T> recipes = new $T<>()", List.class, org.bukkit.inventory.Recipe.class, ArrayList.class)
            .addCode(
                defaultMethods.stream()
                    .map((method) -> CodeBlock.of("recipes.addAll($N())", method))
                    .collect(CodeBlockStatementCollector.collect())
            )
            .addStatement("return recipes")
            .build();

        TypeSpec recipeManagerTypeSpec = TypeSpec.classBuilder("RecipeManager")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(AbstractRecipeManager.class)
            .addMethod(defaultRecipesMethod)
            .addMethods(defaultMethods)
            .addMethods(recipeMethods.values().stream().flatMap(List::stream).collect(Collectors.toList()))
            .build();

        JavaFile recipeManagerJavaFile = JavaFile.builder(generatedClassNamespace, recipeManagerTypeSpec)
            .indent("    ")
            .build();

        try {
            recipeManagerJavaFile.writeTo(generatedClassPath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
