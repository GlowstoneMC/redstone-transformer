package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.loader.model.external.recipe.ShapelessRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShapelessRecipeGenerator implements RecipeGenerator<ShapelessRecipe> {
    @Override
    public Class<ShapelessRecipe> getAssociatedClass() {
        return ShapelessRecipe.class;
    }

    @Override
    public String getDefaultMethodName() {
        return "defaultShapelessRecipes";
    }

    @Override
    public MethodSpec generateMethodImpl(String namespaceName,
                                         String itemName,
                                         Map<String, Map<String, List<String>>> namespacedTaggedItems,
                                         ShapelessRecipe shapelessRecipe) {
        CodeBlock.Builder methodBlock = CodeBlock.builder()
            .addStatement(
                "$T key = new $T($S, $S)",
                NamespacedKey.class,
                NamespacedKey.class,
                namespaceName,
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
                                    namespacedKey = new String[] {namespaceName, namespacedKey[0]};
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

        MethodSpec methodSpec = MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(ParameterizedTypeName.get(
                List.class,
                org.bukkit.inventory.ShapelessRecipe.class
            ))
            .addCode(methodBlock.build())
            .build();

        return methodSpec;
    }
}
