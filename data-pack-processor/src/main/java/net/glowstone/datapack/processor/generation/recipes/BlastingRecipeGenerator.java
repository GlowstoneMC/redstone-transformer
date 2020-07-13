package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.glowstone.datapack.loader.model.external.recipe.BlastingRecipe;
import net.glowstone.datapack.processor.generation.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BlastingRecipeGenerator implements RecipeGenerator<BlastingRecipe> {
    @Override
    public Class<BlastingRecipe> getAssociatedClass() {
        return BlastingRecipe.class;
    }

    @Override
    public String getDefaultMethodName() {
        return "defaultBlastingRecipes";
    }

    @Override
    public MethodSpec generateMethodImpl(String namespaceName,
                                         String itemName,
                                         Map<String, Map<String, Set<String>>> namespacedTaggedItems,
                                         BlastingRecipe blastingRecipe) {
        Material material = Material.matchMaterial(blastingRecipe.getResult());

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
                1
            )
            .addStatement(
                "String group = $S",
                blastingRecipe.getGroup().orElse("")
            )
            .addStatement(
                "$T recipes = new $T<>()",
                ParameterizedTypeName.get(
                    List.class,
                    org.bukkit.inventory.BlastingRecipe.class
                ),
                ArrayList.class
            )
            .addStatement("$T recipe", org.bukkit.inventory.BlastingRecipe.class);

        Set<String> untaggedIngredientOptions = blastingRecipe.getIngredient()
            .stream()
            .flatMap(
                (item) -> ItemUtils.untagItem(namespacedTaggedItems, namespaceName, item)
            )
            .collect(Collectors.toSet());

        for (String ingredient : untaggedIngredientOptions) {
            methodBlock.addStatement(
                "recipe = new $T(key, results, $T.$L, $Lf, $L)",
                org.bukkit.inventory.BlastingRecipe.class,
                Material.class,
                Material.matchMaterial(ingredient),
                blastingRecipe.getExperience(),
                blastingRecipe.getCookingTime()
            );
            methodBlock.addStatement(
                "recipe.setGroup(group)"
            );
            methodBlock.addStatement(
                "recipes.add(recipe)"
            );
        }

        methodBlock.addStatement("return recipes");

        return MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(ParameterizedTypeName.get(
                List.class,
                org.bukkit.inventory.BlastingRecipe.class
            ))
            .addCode(methodBlock.build())
            .build();
    }
}
