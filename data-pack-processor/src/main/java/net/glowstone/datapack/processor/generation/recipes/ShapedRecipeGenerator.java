package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.glowstone.datapack.loader.model.external.recipe.ShapedRecipe;
import net.glowstone.datapack.processor.generation.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

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
                                         Map<String, Map<String, Set<String>>> namespacedTaggedItems,
                                         ShapedRecipe shapedRecipe) {
        Material material = Material.matchMaterial(shapedRecipe.getResult().getItem());

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
                shapedRecipe.getResult().getCount()
            )
            .addStatement(
                "String group = $S",
                shapedRecipe.getGroup().orElse("")
            )
            .addStatement(
                "$T recipes = new $T<>()",
                ParameterizedTypeName.get(
                    List.class,
                    org.bukkit.inventory.ShapedRecipe.class
                ),
                ArrayList.class
            )
            .addStatement(
                CodeBlock.builder()
                    .add(
                        "$T[] shape = new $T[] {",
                        String.class,
                        String.class
                    )
                    .add(
                        shapedRecipe.getPattern()
                            .stream()
                            .map((pattern) -> CodeBlock.of("$S", pattern))
                            .collect(CodeBlock.joining(", "))
                    )
                    .add("}")
                    .build()
            )
            .addStatement("$T recipe", org.bukkit.inventory.ShapedRecipe.class);

        Map<Character, Set<String>> untaggedItemKeys = shapedRecipe.getKey()
            .entrySet()
            .stream()
            .map(
                (entry) -> new SimpleEntry<>(
                    entry.getKey(),
                    entry.getValue()
                        .stream()
                        .flatMap((item) -> ItemUtils.untagItem(namespacedTaggedItems, namespaceName, item))
                        .collect(Collectors.toSet())
                )
            )
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        Set<Map<Character, String>> itemKeyCombinations = cartesianProduct(untaggedItemKeys);

        itemKeyCombinations.forEach((itemKey) -> {
            methodBlock.addStatement(
                "recipe = new $T(key, results)",
                org.bukkit.inventory.ShapedRecipe.class
            );
            itemKey.forEach((key, ingredient) -> {
                methodBlock.addStatement(
                    "recipe.setIngredient('$L', $T.$L)",
                    key,
                    Material.class,
                    Material.matchMaterial(ingredient)
                );
            });
            methodBlock.addStatement(
                "recipe.shape(shape)"
            );
            methodBlock.addStatement(
                "recipe.setGroup(group)"
            );
            methodBlock.addStatement(
                "recipes.add(recipe)"
            );
        });

        methodBlock.addStatement("return recipes");

        return MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(ParameterizedTypeName.get(
                List.class,
                org.bukkit.inventory.ShapedRecipe.class
            ))
            .addCode(methodBlock.build())
            .build();
    }

    private static <K, V> Set<Map<K, V>> cartesianProduct(Map<K, Set<V>> options) {
        Set<Map<K, V>> results = new HashSet<>();

        List<K> keys = new ArrayList<>();
        List<Set<V>> values = new ArrayList<>();
        options.forEach((key, value) -> {
            keys.add(key);
            values.add(value);
        });

        return Sets.cartesianProduct(values)
            .stream()
            .map((combo) -> {
                Map<K, V> map = new HashMap<>();

                for (int i = 0; i < combo.size(); i++) {
                    map.put(keys.get(i), combo.get(i));
                }

                return map;
            })
            .collect(Collectors.toSet());
    }
}
