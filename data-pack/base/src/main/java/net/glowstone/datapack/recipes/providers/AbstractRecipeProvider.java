package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.Streams;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.Item;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.recipes.inputs.RecipeInput;
import net.glowstone.datapack.utils.NamespaceUtils;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.ClassConstructorMappingArgument;
import net.glowstone.datapack.utils.mapping.EnumMappingArgument;
import net.glowstone.datapack.utils.mapping.TagMappingArgument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.RecipeChoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public abstract class AbstractRecipeProvider<RE extends Recipe, RI extends RecipeInput> implements RecipeProvider<RE, RI> {
    private final NamespacedKey key;

    protected AbstractRecipeProvider(NamespacedKey key) {
        this.key = key;
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    @Override
    public Optional<org.bukkit.inventory.Recipe> getRecipeGeneric(RecipeInput input) {
        //noinspection unchecked
        return this.getRecipeFor((RI) input);
    }

    protected static abstract class AbstractRecipeProviderFactory<P extends RecipeProvider<RE, RI>, RE extends Recipe, RI extends RecipeInput> implements RecipeProviderFactory<P, RE, RI> {
        private final Class<P> recipeProviderType;
        private final Class<RE> modelType;
        private final Class<RI> inputClass;

        protected AbstractRecipeProviderFactory(Class<P> recipeProviderType, Class<RE> modelType, Class<RI> inputClass) {
            this.recipeProviderType = recipeProviderType;
            this.modelType = modelType;
            this.inputClass = inputClass;
        }

        @Override
        public final Class<P> getRecipeProviderType() {
            return this.recipeProviderType;
        }

        @Override
        public final Class<RE> getModelType() {
            return modelType;
        }

        @Override
        public final Class<RI> getInputClass() {
            return this.inputClass;
        }

        protected static RecipeChoice generateRecipeChoice(TagManager tagManager, String namespace, List<Item> items) {
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

        protected static AbstractMappingArgument generateRecipeChoiceMapping(String namespace, List<Item> items) {
            return generateFromMaterialTags(namespace, items, (materials, tags) -> {
                if (tags.size() > 0) {
                    return new ClassConstructorMappingArgument(
                        MaterialTagRecipeChoice.class,
                        Streams
                            .concat(
                                materials.stream().map(EnumMappingArgument::new),
                                tags.stream().map((t) -> new TagMappingArgument(Tag.REGISTRY_ITEMS, t, Material.class))
                            )
                            .collect(Collectors.toList())
                    );
                } else {
                    return new ClassConstructorMappingArgument(
                        RecipeChoice.MaterialChoice.class,
                        materials.stream().map(EnumMappingArgument::new).collect(Collectors.toList())
                    );
                }
            });
        }

        protected static <T> T generateFromMaterialTags(String namespace, List<Item> items, BiFunction<List<Material>, List<NamespacedKey>, T> generator) {
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
