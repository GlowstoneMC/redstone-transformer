package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.loader.model.external.recipe.ShapelessRecipe;
import net.glowstone.datapack.processor.generation.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                                         Map<String, Map<String, Set<String>>> namespacedTaggedItems,
                                         ShapelessRecipe shapelessRecipe) {
        Material material = Material.matchMaterial(shapelessRecipe.getResult().getItem());

        CodeBlock.Builder methodBlock = CodeBlock.builder()
            .addStatement(
                "$T key = new $T($S, $S)",
                NamespacedKey.class,
                NamespacedKey.class,
                namespaceName,
                itemName
            )
            .addStatement(
                "$T results = new $T($T.$L, $L)",
                ItemStack.class,
                ItemStack.class,
                Material.class,
                material,
                shapelessRecipe.getResult().getCount()
            )
            .addStatement(
                "String group = $S",
                shapelessRecipe.getGroup().orElse("")
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
                (ingredientStack) -> {
                    List<List<String>> untaggedItemNames = ingredientStack.stream()
                        .map((item) -> ItemUtils.untagItem(namespacedTaggedItems, namespaceName, item).collect(Collectors.toList()))
                        .collect(Collectors.toList());

                    return Lists.cartesianProduct(untaggedItemNames).stream();
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

        return MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(ParameterizedTypeName.get(
                List.class,
                org.bukkit.inventory.ShapelessRecipe.class
            ))
            .addCode(methodBlock.build())
            .build();
    }
}
