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
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.processor.generation.CodeBlockStatementCollector;
import net.glowstone.datapack.processor.generation.DataPackItemSourceGenerator;
import net.glowstone.datapack.processor.generation.MappingArgumentGenerator;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.recipes.providers.RecipeProviderRegistry;
import net.glowstone.datapack.recipes.providers.RecipeProviderRegistry.RecipeProviderMappingArgumentsResult;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeManagerGenerator implements DataPackItemSourceGenerator {
    private final List<MethodSpec> recipeMethods = new ArrayList<>();

    @Override
    public void addItems(String namespaceName,
                         Data data) {
        data.getRecipes().forEach((itemName, recipe) -> {
            Optional<RecipeProviderMappingArgumentsResult> result = RecipeProviderRegistry.providerArguments(namespaceName, itemName, recipe);

            if (result.isPresent()) {
                List<MappingArgument> arguments = result.get().getMappingArguments();
                Class<? extends RecipeProvider<?>> recipeProviderType = result.get().getRecipeProviderType();

                CodeBlock.Builder initBlock = CodeBlock.builder()
                    .add(
                        "return $T.factory().provider(",
                        recipeProviderType
                    );

                initBlock.add(
                    arguments.stream()
                        .map((v) -> MappingArgumentGenerator.mapArgument("this.tagManager", v))
                        .collect(CodeBlock.joining(", "))
                );

                initBlock.add(")");

                MethodSpec method = MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
                    .addModifiers(Modifier.PRIVATE)
                    .returns(recipeProviderType)
                    .addStatement(initBlock.build())
                    .build();

                recipeMethods.add(method);
            } else {
                throw new IllegalArgumentException("Cannot create recipe " + namespaceName + ":" + itemName + " because its of unknown type " + recipe.getClass());
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
            .addParameter(TagManager.class, "tagManager")
            .addStatement("super(tagManager)")
            .build();

        TypeSpec recipeManagerTypeSpec = TypeSpec.classBuilder("VanillaRecipeManager")
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
