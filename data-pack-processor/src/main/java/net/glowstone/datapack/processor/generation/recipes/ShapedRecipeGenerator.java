package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.loader.model.external.recipe.ShapedRecipe;
import net.glowstone.datapack.processor.generation.utils.ItemUtils;
import net.glowstone.datapack.processor.generation.utils.NamespaceUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import javax.lang.model.element.Modifier;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class ShapedRecipeGenerator implements RecipeGenerator<ShapedRecipe> {
    @Override
    public Class<ShapedRecipe> getAssociatedClass() {
        return ShapedRecipe.class;
    }

    @Override
    public String getDefaultMethodName() {
        return "defaultShapedRecipes";
    }

    @Override
    public MethodSpec generateMethodImpl(String namespaceName,
                                         String itemName,
                                         ShapedRecipe shapedRecipe) {
        Material material = Material.matchMaterial(shapedRecipe.getResult().getItem());

        CodeBlock.Builder methodBlock = CodeBlock.builder()
            .addStatement(
                "$T recipe = new $T(new $T($S, $S), new $T($T.$L, $L))",
                org.bukkit.inventory.ShapedRecipe.class,
                org.bukkit.inventory.ShapedRecipe.class,
                NamespacedKey.class,
                namespaceName,
                itemName,
                ItemStack.class,
                Material.class,
                material,
                shapedRecipe.getResult().getCount()
            );

        shapedRecipe.getGroup().ifPresent((group) -> methodBlock.addStatement("recipe.setGroup($S)", group));

        methodBlock.addStatement(
            CodeBlock.builder()
                .add(
                    "recipe.shape(new $T[] {",
                    String.class
                )
                .add(
                    shapedRecipe.getPattern()
                        .stream()
                        .map((pattern) -> CodeBlock.of("$S", pattern))
                        .collect(CodeBlock.joining(", "))
                )
                .add("})")
                .build()
        );

        shapedRecipe.getKey().forEach((key, ingredients) -> {
            CodeBlock.Builder ingredientBlock = CodeBlock.builder()
                .add(
                    "new $T($T.<$T>builder()",
                    RecipeChoice.MaterialChoice.class,
                    ImmutableList.class,
                    Material.class
                );
            for (Item ingredient : ingredients) {
                if (ingredient.getItem().isPresent()) {
                    ingredientBlock.add(
                        ".add($T.$L)",
                        Material.class,
                        Material.matchMaterial(ingredient.getItem().get())
                    );
                } else {
                    NamespacedKey tagKey = NamespaceUtils.parseNamespace(ingredient.getTag().get(), namespaceName);
                    ingredientBlock.add(
                        ".addAll(this.tagManager.<$T>getItemTag(new $T($S, $S)).getValues())",
                        Material.class,
                        NamespacedKey.class,
                        tagKey.getNamespace(),
                        tagKey.getKey()
                    );
                }
            }
            ingredientBlock.add(".build())");

            methodBlock.addStatement(
                "recipe.setIngredient('$L', $L)",
                key,
                ingredientBlock.build()
            );
        });

        methodBlock.addStatement("return recipe");

        return MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(org.bukkit.inventory.ShapedRecipe.class)
            .addCode(methodBlock.build())
            .build();
    }
}
