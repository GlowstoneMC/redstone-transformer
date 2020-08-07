package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.Streams;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import net.glowstone.datapack.utils.NamespaceUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.RecipeChoice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

class MappingUtils {
    static RecipeChoice generateRecipeChoice(AbstractTagManager tagManager, String namespace, List<Item> items) {
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

    static MappingArgument generateRecipeChoiceMapping(String namespace, List<Item> items) {
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

    static <T> T generateFromMaterialTags(String namespace, List<Item> items, BiFunction<List<Material>, List<NamespacedKey>, T> generator) {
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
