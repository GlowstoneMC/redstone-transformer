package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.glowstone.datapack.loader.model.external.recipe.BlastingRecipe;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.processor.generation.utils.ItemUtils;
import net.glowstone.datapack.processor.generation.utils.NamespaceUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

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
                                         BlastingRecipe blastingRecipe) {
        Material material = Material.matchMaterial(blastingRecipe.getResult());

        CodeBlock.Builder ingredientBlock = CodeBlock.builder()
            .add(
                "new $T($T.<$T>builder()",
                RecipeChoice.MaterialChoice.class,
                ImmutableList.class,
                Material.class
            );
        for (Item ingredient : blastingRecipe.getIngredient()) {
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

        CodeBlock.Builder methodBlock = CodeBlock.builder()
            .addStatement(
                "$T recipe = new $T(new $T($S, $S), new $T($T.$L, $L), $L, $Lf, $L)",
                org.bukkit.inventory.BlastingRecipe.class,
                org.bukkit.inventory.BlastingRecipe.class,
                NamespacedKey.class,
                namespaceName,
                itemName,
                ItemStack.class,
                Material.class,
                material,
                1,
                ingredientBlock.build(),
                blastingRecipe.getExperience(),
                blastingRecipe.getCookingTime()
            );

        blastingRecipe.getGroup().ifPresent((group) -> methodBlock.addStatement("recipe.setGroup($S)", group));

        methodBlock.addStatement("return recipe");

        return MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(org.bukkit.inventory.BlastingRecipe.class)
            .addCode(methodBlock.build())
            .build();
    }
}
