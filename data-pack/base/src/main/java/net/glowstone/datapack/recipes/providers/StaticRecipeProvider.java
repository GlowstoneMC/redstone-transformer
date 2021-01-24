package net.glowstone.datapack.recipes.providers;

import com.google.common.base.Preconditions;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.recipes.inputs.RecipeInput;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Stream;

import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public abstract class StaticRecipeProvider<RE extends Recipe, RI extends RecipeInput, RB extends org.bukkit.inventory.Recipe> extends AbstractRecipeProvider<RE, RI> {
    private static <RB extends org.bukkit.inventory.Recipe> NamespacedKey getKeyFromRecipe(RB recipe) {
        Preconditions.checkArgument(Keyed.class.isAssignableFrom(recipe.getClass()), "Static recipes must implement Bukkit interface Keyed.");
        return ((Keyed) recipe).getKey();
    }

    private final RB recipe;

    protected StaticRecipeProvider(RB recipe) {
        super(getKeyFromRecipe(recipe));
        this.recipe = recipe;
    }

    public RB getRecipe() {
        return this.recipe;
    }

    @Override
    public Stream<org.bukkit.inventory.Recipe> getRecipesForResult(ItemStack result) {
        org.bukkit.inventory.Recipe recipe = getRecipe();

        if (matchesWildcard(result, recipe.getResult())) {
            return Stream.of(recipe);
        }

        return Stream.empty();
    }

    @Override
    public abstract StaticRecipeProviderFactory<? extends StaticRecipeProvider<RE, RI, RB>, RE, RI, RB> getFactory();

    public interface StaticRecipeProviderFactory<P extends StaticRecipeProvider<RE, RI, RB>,
                                                 RE extends Recipe,
                                                 RI extends RecipeInput,
                                                 RB extends org.bukkit.inventory.Recipe> extends RecipeProviderFactory<P, RE, RI> {
        Class<RB> getBukkitType();
        P provider(RB recipe);
        List<MappingArgument> providerArguments(String namespace, String key, RE recipe);
        P provider(TagManager tagManager, String namespace, String key, RE recipe);
        P providerGeneric(org.bukkit.inventory.Recipe recipe);
    }

    protected static abstract class AbstractStaticRecipeProviderFactory<P extends StaticRecipeProvider<RE, RI, RB>,
                                                                        RE extends Recipe,
                                                                        RI extends RecipeInput,
                                                                        RB extends org.bukkit.inventory.Recipe>
                                                                        extends AbstractRecipeProviderFactory<P, RE, RI>
                                                                        implements StaticRecipeProviderFactory<P, RE, RI, RB> {
        private final Class<RB> bukkitType;

        protected AbstractStaticRecipeProviderFactory(Class<P> recipeProviderType,
                                                      Class<RE> modelType,
                                                      Class<RI> inputType,
                                                      Class<RB> bukkitType) {
            super(recipeProviderType, modelType, inputType);
            this.bukkitType = bukkitType;
        }

        @Override
        public final Class<RB> getBukkitType() {
            return bukkitType;
        }

        @Override
        public P providerGeneric(org.bukkit.inventory.Recipe recipe) {
            //noinspection unchecked
            return this.provider((RB) recipe);
        }
    }
}
