package net.glowstone.datapack.processor.generation.recipes;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import net.glowstone.datapack.loader.model.external.recipe.GroupableRecipe;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.processor.generation.utils.NamespaceUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCraftingRecipeGenerator<T1 extends Recipe, T2 extends org.bukkit.inventory.Recipe> implements RecipeGenerator<T1, T2> {
    @Override
    public String getDefaultMethodName() {
        return "default" + getBukkitClass().getSimpleName() + "s";
    }

    @Override
    @SuppressWarnings("unchecked")
    public MethodSpec generateMethod(String namespaceName, String itemName, Recipe recipe) {
        T1 recipeImpl = (T1) recipe;

        Material material = getResultingMaterial(recipeImpl);

        CodeBlock.Builder initBlock = CodeBlock.builder()
            .add(
                "$T recipe = new $T(new $T($S, $S), new $T($T.$L, $L)",
                getBukkitClass(),
                getBukkitClass(),
                NamespacedKey.class,
                namespaceName,
                itemName,
                ItemStack.class,
                Material.class,
                material,
                getResultCount(recipeImpl)
            );
        extraConstructorArgs(namespaceName, itemName, recipeImpl)
            .ifPresent((extra) -> initBlock.add(", $L", extra));
        initBlock.add(")");

        CodeBlock.Builder methodBlock = CodeBlock.builder()
            .addStatement(initBlock.build());

        if (recipeImpl instanceof GroupableRecipe) {
            Optional<String> group = ((GroupableRecipe)recipeImpl).getGroup();
            group.ifPresent((groupName) -> methodBlock.addStatement("recipe.setGroup($S)", groupName));
        }

        extraRecipeCode(namespaceName, itemName, recipeImpl, material).ifPresent(methodBlock::add);

        methodBlock.addStatement("return recipe");

        return MethodSpec.methodBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, itemName))
            .addModifiers(Modifier.PRIVATE)
            .returns(getBukkitClass())
            .addCode(methodBlock.build())
            .build();
    }

    protected Optional<CodeBlock> extraConstructorArgs(String namespaceName, String itemName, T1 recipe) {
        return Optional.empty();
    }

    protected Optional<CodeBlock> extraRecipeCode(String namespaceName, String itemName, T1 recipe, Material resultingMaterial) {
        return Optional.empty();
    }

    protected abstract Material getResultingMaterial(T1 recipe);

    protected abstract int getResultCount(T1 recipe);

    protected static class Helpers {
        public static CodeBlock createRecipeChoice(String namespaceName, List<Item> items) {
            return CodeBlock.of(
                "materialChoice($L)",
                items.stream()
                    .map((item) -> {
                        Optional<String> itemName = item.getItem();
                        if (itemName.isPresent()) {
                            return CodeBlock.of(
                                "$T.$L",
                                Material.class,
                                Material.matchMaterial(itemName.get())
                            );
                        }

                        Optional<String> tagName = item.getTag();
                        if (tagName.isPresent()) {
                            NamespacedKey tagKey = NamespaceUtils.parseNamespace(tagName.get(), namespaceName);
                            return CodeBlock.of(
                                "this.tagManager.getItemTag($S, $S)",
                                tagKey.getNamespace(),
                                tagKey.getKey()
                            );
                        }

                        throw new IllegalStateException("Item encountered with neither item name nor tag name!");
                    })
                    .collect(CodeBlock.joining(", "))
            );
        }
    }

}
