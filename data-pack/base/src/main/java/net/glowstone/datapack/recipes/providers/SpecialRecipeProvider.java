package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.loader.model.external.recipe.special.ArmorDyeRecipe;
import net.glowstone.datapack.recipes.inputs.RecipeInput;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class SpecialRecipeProvider<I extends RecipeInput> extends AbstractRecipeProvider<I> {
    protected SpecialRecipeProvider(Class<I> inventoryClass, NamespacedKey key) {
        super(inventoryClass, key);
    }

    @Override
    public Stream<org.bukkit.inventory.Recipe> getRecipesForResult(ItemStack result) {
        return Stream.empty();
    }

    public interface SpecialRecipeProviderFactory<P extends RecipeProvider<?>, RE extends Recipe> extends RecipeProviderFactory<P, RE> {
        List<MappingArgument> providerArguments(String namespace, String key);
        P provider(String namespace, String key);
    }

    protected static abstract class AbstractSpecialRecipeProviderFactory<P extends RecipeProvider<?>, RE extends Recipe> implements SpecialRecipeProviderFactory<P, RE> {
        private final Class<RE> modelType;
        private final Class<P> recipeProviderType;
        private final SpecialRecipeProviderConstructor<P> constructor;

        protected AbstractSpecialRecipeProviderFactory(Class<RE> modelType,
                                                       Class<P> recipeProviderType,
                                                       SpecialRecipeProviderConstructor<P> constructor) {
            this.modelType = modelType;
            this.recipeProviderType = recipeProviderType;
            this.constructor = constructor;
        }

        @Override
        public Class<RE> getModelType() {
            return this.modelType;
        }

        @Override
        public Class<P> getRecipeProviderType() {
            return this.recipeProviderType;
        }

        @Override
        public List<MappingArgument> providerArguments(String namespace, String key) {
            return ImmutableList.of(
                MappingArgument.forString(namespace),
                MappingArgument.forString(key)
            );
        }

        @Override
        public P provider(String namespace, String key) {
            return constructor.create(namespace, key);
        }
    }

    @FunctionalInterface
    protected interface SpecialRecipeProviderConstructor<P extends RecipeProvider<?>> {
        P create(String namespace, String key);
    }
}
