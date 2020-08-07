package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import net.glowstone.datapack.AbstractRecipeManager;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.processor.generation.CodeBlockStatementCollector;
import net.glowstone.datapack.processor.generation.DataPackItemSourceGenerator;
import net.glowstone.datapack.processor.generation.MappingArgumentGenerator;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.recipes.providers.mapping.RecipeProviderMapping;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecipeManagerGenerator implements DataPackItemSourceGenerator {
    private final List<MethodSpec> recipeMethods = new ArrayList<>();

    @Override
    public void addItems(String namespaceName,
                         Data data) {
        data.getRecipes().forEach((itemName, recipe) -> {
            RecipeProviderMapping<?, ?> mapping = RecipeProviderMapping.RECIPE_PROVIDER_MAPPINGS.get(recipe.getClass());

            if (mapping != null) {
                List<MappingArgument> arguments = mapping.providerArgumentsGeneric(namespaceName, itemName, recipe);

                CodeBlock.Builder initBlock = CodeBlock.builder()
                    .add(
                        "return new $T(",
                        mapping.getRecipeProviderType()
                    );

                initBlock.add(
                    arguments.stream()
                        .map((v) -> MappingArgumentGenerator.mapArgument("this.tagManager", v))
                        .collect(CodeBlock.joining(", "))
                );

                initBlock.add(")");

                MethodSpec method = MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
                    .addModifiers(Modifier.PRIVATE)
                    .returns(mapping.getRecipeProviderType())
                    .addStatement(initBlock.build())
                    .build();

                recipeMethods.add(method);
            }
        });
    }

    @Override
    public void generateManager(Path generatedClassPath,
                                String generatedClassNamespace) {
        ParameterizedTypeName recipeListType = ParameterizedTypeName.get(
            ClassName.get(List.class),
            ParameterizedTypeName.get(
                ClassName.get(RecipeProvider.class),
                WildcardTypeName.subtypeOf(Object.class)
            )
        );

        MethodSpec defaultRecipesMethod = MethodSpec.methodBuilder("defaultRecipes")
            .addModifiers(Modifier.PROTECTED)
            .returns(recipeListType)
            .addStatement(
                "$T recipes = new $T<>()",
                recipeListType,
                ArrayList.class
            )
            .addCode(
                recipeMethods.stream()
                    .map((method) -> CodeBlock.of("recipes.add($N())", method))
                    .collect(CodeBlockStatementCollector.collect())
            )
            .addStatement("return recipes")
            .build();

        MethodSpec constructorMethod = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(AbstractTagManager.class, "tagManager")
            .addStatement("super(tagManager)")
            .build();

        TypeSpec recipeManagerTypeSpec = TypeSpec.classBuilder("RecipeManager")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(AbstractRecipeManager.class)
            .addMethod(constructorMethod)
            .addMethod(defaultRecipesMethod)
            .addMethods(recipeMethods)
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
