package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.Streams;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.recipes.inputs.RecipeInput;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.utils.NamespaceUtils;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface RecipeProvider<I extends RecipeInput> {
    NamespacedKey getKey();
    Class<I> getInputClass();
    Optional<org.bukkit.inventory.Recipe> getRecipeFor(I input);
    Stream<org.bukkit.inventory.Recipe> getRecipesForResult(ItemStack result);
    @SuppressWarnings("unchecked")
    default Optional<org.bukkit.inventory.Recipe> getRecipeGeneric(RecipeInput input) {
        return this.getRecipeFor((I) input);
    }

    interface RecipeProviderFactory<P extends RecipeProvider<?>, RE extends Recipe> {
        Class<RE> getModelType();
        Class<P> getRecipeProviderType();
    }

    class RecipeProviderFactoryUtils {
        public static RecipeChoice generateRecipeChoice(TagManager tagManager, String namespace, List<Item> items) {
            return generateFromMaterialTags(namespace, items, (materials, tags) -> {
                if (tags.size() > 0) {
                    return new MaterialTagRecipeChoice(
                        Streams
                            .concat(
                                materials.stream(),
                                tags.stream().map(tagManager::getItemTag)
                            )
                            .collect(Collectors.toList())
                    );
                } else {
                    return new RecipeChoice.MaterialChoice(materials);
                }
            });
        }

        public static MappingArgument generateRecipeChoiceMapping(String namespace, List<Item> items) {
            return generateFromMaterialTags(namespace, items, (materials, tags) -> {
                if (tags.size() > 0) {
                    return MappingArgument.forClassConstructor(
                        MaterialTagRecipeChoice.class,
                        Streams
                            .concat(
                                materials.stream().map(MappingArgument::forEnum),
                                tags.stream().map((t) -> MappingArgument.forTag(Tag.REGISTRY_ITEMS, t, Material.class))
                            )
                            .collect(Collectors.toList())
                    );
                } else {
                    return MappingArgument.forClassConstructor(
                        RecipeChoice.MaterialChoice.class,
                        materials.stream().map(MappingArgument::forEnum).collect(Collectors.toList())
                    );
                }
            });
        }

        public static <T> T generateFromMaterialTags(String namespace, List<Item> items, BiFunction<List<Material>, List<NamespacedKey>, T> generator) {
            List<Material> materials = new ArrayList<>();
            List<NamespacedKey> tags = new ArrayList<>();

            items.forEach((item) -> {
                if (item.getItem().isPresent()) {
                    materials.add(Material.matchMaterial(item.getItem().get()));
                } else if (item.getTag().isPresent()) {
                    tags.add(NamespaceUtils.parseNamespace(item.getTag().get(), namespace));
                }
            });

            return generator.apply(materials, tags);
        }
    }
}
