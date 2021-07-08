package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.inputs.RecipeInput;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.StringMappingArgument;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Stream;

public abstract class SpecialRecipeProvider<RE extends Recipe, RI extends RecipeInput> extends AbstractRecipeProvider<RE, RI> {
    protected SpecialRecipeProvider(NamespacedKey key) {
        super(key);
    }

    @Override
    public Stream<org.bukkit.inventory.Recipe> getRecipesForResult(ItemStack result) {
        return Stream.empty();
    }

    @Override
    public abstract SpecialRecipeProviderFactory<? extends SpecialRecipeProvider<RE, RI>, RE, RI> getFactory();

    public interface SpecialRecipeProviderFactory<P extends RecipeProvider<RE, RI>, RE extends Recipe, RI extends RecipeInput> extends RecipeProviderFactory<P, RE, RI> {
        List<AbstractMappingArgument> providerArguments(String namespace, String key);
        P provider(String namespace, String key);
    }

    protected static abstract class AbstractSpecialRecipeProviderFactory<P extends SpecialRecipeProvider<RE, RI>, RE extends Recipe, RI extends RecipeInput> extends AbstractRecipeProviderFactory<P, RE, RI> implements SpecialRecipeProviderFactory<P, RE, RI> {
        private final SpecialRecipeProviderConstructor<P, RE, RI> constructor;

        protected AbstractSpecialRecipeProviderFactory(Class<P> recipeProviderType,
                                                       Class<RE> modelType,
                                                       Class<RI> inputType,
                                                       SpecialRecipeProviderConstructor<P, RE, RI> constructor) {
            super(recipeProviderType, modelType, inputType);
            this.constructor = constructor;
        }

        @Override
        public List<AbstractMappingArgument> providerArguments(String namespace, String key) {
            return ImmutableList.of(
                new StringMappingArgument(namespace),
                new StringMappingArgument(key)
            );
        }

        @Override
        public P provider(String namespace, String key) {
            return constructor.create(namespace, key);
        }
    }

    @FunctionalInterface
    protected interface SpecialRecipeProviderConstructor<P extends SpecialRecipeProvider<RE, RI>, RE extends Recipe, RI extends RecipeInput> {
        P create(String namespace, String key);
    }
}
