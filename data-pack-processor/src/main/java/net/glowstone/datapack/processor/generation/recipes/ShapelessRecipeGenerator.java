package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.loader.model.external.recipe.ShapelessRecipe;
import net.glowstone.datapack.processor.generation.utils.NamespaceUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import javax.lang.model.element.Modifier;
import java.util.List;

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
                                         ShapelessRecipe shapelessRecipe) {
        Material material = Material.matchMaterial(shapelessRecipe.getResult().getItem());

        CodeBlock.Builder methodBlock = CodeBlock.builder()
            .addStatement(
                "$T recipe = new $T(new $T($S, $S), new $T($T.$L, $L))",
                org.bukkit.inventory.ShapelessRecipe.class,
                org.bukkit.inventory.ShapelessRecipe.class,
                NamespacedKey.class,
                namespaceName,
                itemName,
                ItemStack.class,
                Material.class,
                material,
                shapelessRecipe.getResult().getCount()
            );

        shapelessRecipe.getGroup().ifPresent((group) -> methodBlock.addStatement("recipe.setGroup($S)", group));

        for (List<Item> ingredientsStack : shapelessRecipe.getIngredients()) {
            CodeBlock.Builder ingredientBlock = CodeBlock.builder()
                .add(
                    "recipe.addIngredient(new $T($T.<$T>builder()",
                    RecipeChoice.MaterialChoice.class,
                    ImmutableList.class,
                    Material.class
                );
            for (Item ingredient : ingredientsStack) {
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
            ingredientBlock.add(".build()))");

            methodBlock.addStatement(ingredientBlock.build());
        }

        methodBlock.addStatement("return recipe");

        return MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(org.bukkit.inventory.ShapelessRecipe.class)
            .addCode(methodBlock.build())
            .build();
    }
}
