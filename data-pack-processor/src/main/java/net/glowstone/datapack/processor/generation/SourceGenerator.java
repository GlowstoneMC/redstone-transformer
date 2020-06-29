package net.glowstone.datapack.processor.generation;

import com.google.common.collect.Lists;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import net.glowstone.datapack.AbstractRecipeManager;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.loader.model.external.recipe.ShapedRecipe;
import net.glowstone.datapack.loader.model.external.recipe.ShapelessRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SourceGenerator {
    public void generateSources(Path generatedClassPath,
                                String generatedClassNamespace,
                                DataPack dataPack) {
        Map<String, Map<String, List<String>>> namespacedTaggedItems = createNamespacedTaggedItemsMap(dataPack);
        Stream<NamespacedItem<Recipe>> recipes = streamRecipes(dataPack);

        generateRecipeSources(generatedClassPath, generatedClassNamespace, namespacedTaggedItems, recipes);
    }

    private Map<String, Map<String, List<String>>> createNamespacedTaggedItemsMap(DataPack dataPack) {
        return dataPack.getNamespacedData()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Entry::getKey,
                (dataEntry) -> Stream.concat(
                    dataEntry.getValue()
                        .getItemTags()
                        .entrySet()
                        .stream(),
                    dataEntry.getValue()
                        .getBlockTags()
                        .entrySet()
                        .stream()
                ).collect(Collectors.toMap(
                    Entry::getKey,
                    (tagEntry) -> tagEntry.getValue().getValues()
                ))
            ));
    }

    private Stream<NamespacedItem<Recipe>> streamRecipes(DataPack dataPack) {
        return streamItems(dataPack, Data::getRecipes);
    }

    private <T> Stream<NamespacedItem<T>> streamItems(DataPack dataPack, DataItemGetter<T> getter) {
        return dataPack.getNamespacedData()
            .entrySet()
            .stream()
            .flatMap(
                (namespacedEntry) -> getter.getItemMap(namespacedEntry.getValue())
                    .entrySet()
                    .stream()
                    .map((itemEntry) -> new NamespacedItem<>(
                        namespacedEntry.getKey(),
                        itemEntry.getKey(),
                        itemEntry.getValue()
                    ))
            );
    }

    private void generateRecipeSources(Path generatedClassPath,
                                       String generatedClassNamespace,
                                       Map<String, Map<String, List<String>>> namespacedTaggedItems,
                                       Stream<NamespacedItem<Recipe>> items) {
        List<MethodSpec> shapelessRecipesMethods = new ArrayList<>();

        items.forEach((item) -> {
            Recipe recipe = item.getItem();

            if (recipe instanceof ShapelessRecipe) {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe)recipe;

                CodeBlock.Builder methodBlock = CodeBlock.builder()
                    .addStatement(
                        "$T key = new $T($S, $S)",
                        NamespacedKey.class,
                        NamespacedKey.class,
                        item.getNamespaceName(),
                        shapelessRecipe.getGroup()
                    )
                    .addStatement(
                        "$T results = new $T($T.$L, $L)",
                        ItemStack.class,
                        ItemStack.class,
                        Material.class,
                        Material.matchMaterial(shapelessRecipe.getResult().getItem()),
                        shapelessRecipe.getResult().getCount()
                    )
                    .addStatement(
                        "$T recipes = new $T<>()",
                        ParameterizedTypeName.get(
                            List.class,
                            org.bukkit.inventory.ShapelessRecipe.class
                        ),
                        ArrayList.class
                    )
                    .addStatement("$T recipe", org.bukkit.inventory.ShapelessRecipe.class);

                List<List<String>> untaggedIngredientsStacks = shapelessRecipe.getIngredients()
                    .stream()
                    .flatMap(
                        (ingredientsStack) -> {
                            boolean hasTaggedItems = ingredientsStack.stream()
                                .anyMatch((ingredient) -> ingredient.getTag().isPresent());
                            if (hasTaggedItems) {
                                List<List<String>> taggedIngredients = new ArrayList<>();
                                List<String> untaggedIngredients = new ArrayList<>();

                                for (Item ingredient : ingredientsStack) {
                                    if (ingredient.getItem().isPresent()) {
                                        untaggedIngredients.add(ingredient.getItem().get());
                                    } else {
                                        String tag = ingredient.getTag().get();
                                        String[] namespacedKey = tag.split(":", 2);
                                        if (namespacedKey.length == 1) {
                                            namespacedKey = new String[] {item.getNamespaceName(), namespacedKey[0]};
                                        }
                                        taggedIngredients.add(
                                            namespacedTaggedItems.get(namespacedKey[0])
                                                .get(namespacedKey[1])
                                        );
                                    }
                                }

                                List<List<String>> taggedIngredientCombinations = Lists.cartesianProduct(taggedIngredients);
                                return taggedIngredientCombinations.stream()
                                    .map((combination) -> {
                                        List<String> retval = new ArrayList<>();
                                        retval.addAll(combination);
                                        retval.addAll(untaggedIngredients);
                                        return retval;
                                    });
                            } else {
                                return Stream.of(
                                    ingredientsStack.stream()
                                        .map((ingredient) -> ingredient.getItem().get())
                                        .collect(Collectors.toList())
                                );
                            }
                        }
                    )
                    .collect(Collectors.toList());

                for (List<String> ingredientsStack : untaggedIngredientsStacks) {
                    methodBlock.addStatement(
                        "recipe = new $T(key, results)",
                        org.bukkit.inventory.ShapelessRecipe.class
                    );
                    for (String ingredient : ingredientsStack) {
                        methodBlock.addStatement(
                            "recipe.addIngredient($T.$L)",
                            Material.class,
                            Material.matchMaterial(ingredient)
                        );
                    }
                    methodBlock.addStatement(
                        "recipes.add(recipe)"
                    );
                }

                methodBlock.addStatement("return recipes");

                MethodSpec methodSpec = MethodSpec.methodBuilder(item.getItemName())
                    .addModifiers(Modifier.PRIVATE)
                    .returns(ParameterizedTypeName.get(
                        List.class,
                        org.bukkit.inventory.ShapelessRecipe.class
                    ))
                    .addCode(methodBlock.build())
                    .build();

                shapelessRecipesMethods.add(methodSpec);
            }
        });

        MethodSpec defaultShapelessRecipesMethod = MethodSpec.methodBuilder("defaultShapelessRecipes")
            .addModifiers(Modifier.PRIVATE)
            .returns(ParameterizedTypeName.get(List.class, org.bukkit.inventory.ShapelessRecipe.class))
            .addStatement("$T<$T> recipes = new $T<>()", List.class, org.bukkit.inventory.ShapelessRecipe.class, ArrayList.class)
            .addCode(
                shapelessRecipesMethods.stream()
                    .map((method) -> CodeBlock.of("recipes.addAll($N())", method))
                    .collect(CodeBlockStatementCollector.collect())
            )
            .addStatement("return recipes")
            .build();

        MethodSpec defaultRecipesMethod = MethodSpec.methodBuilder("defaultRecipes")
            .addModifiers(Modifier.PROTECTED)
            .returns(ParameterizedTypeName.get(List.class, org.bukkit.inventory.Recipe.class))
            .addStatement("$T<$T> recipes = new $T<>()", List.class, org.bukkit.inventory.Recipe.class, ArrayList.class)
            .addStatement("recipes.addAll($N())", defaultShapelessRecipesMethod)
            .addStatement("return recipes")
            .build();

        TypeSpec recipeManagerTypeSpec = TypeSpec.classBuilder("RecipeManager")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(AbstractRecipeManager.class)
            .addMethod(defaultRecipesMethod)
            .addMethod(defaultShapelessRecipesMethod)
            .addMethods(shapelessRecipesMethods)
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

    private static class NamespacedItem<T> {
        private final String namespaceName;
        private final String itemName;
        private final T item;

        public NamespacedItem(String namespaceName, String itemName, T item) {
            this.namespaceName = Objects.requireNonNull(namespaceName);
            this.itemName = Objects.requireNonNull(itemName);
            this.item = Objects.requireNonNull(item);
        }

        public String getNamespaceName() {
            return namespaceName;
        }

        public String getItemName() {
            return itemName;
        }

        public T getItem() {
            return item;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NamespacedItem that = (NamespacedItem) o;
            return namespaceName.equals(that.namespaceName) &&
                itemName.equals(that.itemName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(namespaceName, itemName);
        }
    }

    @FunctionalInterface
    private interface DataItemGetter<T> {
        Map<String, T> getItemMap(Data data);
    }

    private static class CodeBlockStatementCollector {
        public static Collector<CodeBlock, CodeBlockStatementCollector, CodeBlock> collect() {
            return Collector.of(
                CodeBlockStatementCollector::new,
                CodeBlockStatementCollector::addStatement,
                CodeBlockStatementCollector::merge,
                CodeBlockStatementCollector::combine
            );
        }

        private final CodeBlock.Builder builder;

        private CodeBlockStatementCollector() {
            this.builder = CodeBlock.builder();
        }

        public CodeBlockStatementCollector addStatement(CodeBlock codeBlock) {
            this.builder.addStatement(codeBlock);
            return this;
        }

        public CodeBlockStatementCollector merge(CodeBlockStatementCollector other) {
            CodeBlock otherBlock = other.builder.build();
            if (!otherBlock.isEmpty()) {
                this.builder.add(otherBlock);
            }
            return this;
        }

        public CodeBlock combine() {
            return builder.build();
        }
    }
}
