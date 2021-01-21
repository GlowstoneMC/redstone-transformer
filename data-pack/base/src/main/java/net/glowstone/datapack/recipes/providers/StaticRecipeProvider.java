package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.recipes.inputs.RecipeInput;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Keyed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;
import java.util.stream.Stream;

import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public abstract class StaticRecipeProvider<R extends Recipe & Keyed, I extends RecipeInput> extends AbstractRecipeProvider<I> {
    private R recipe;

    protected StaticRecipeProvider(Class<I> inventoryClass, R recipe) {
        super(inventoryClass, recipe.getKey());
        this.recipe = recipe;
    }

    public R getRecipe() {
        return this.recipe;
    }

    @Override
    public Stream<Recipe> getRecipesForResult(ItemStack result) {
        Recipe recipe = getRecipe();

        if (matchesWildcard(result, recipe.getResult())) {
            return Stream.of(recipe);
        }

        return Stream.empty();
    }

    public interface StaticRecipeProviderFactory<P extends RecipeProvider<?>, RE extends net.glowstone.datapack.loader.model.external.recipe.Recipe, RB extends Recipe> extends RecipeProviderFactory<P, RE> {
        Class<RB> getBukkitType();
        P provider(RB recipe);
        List<MappingArgument> providerArguments(String namespace, String key, RE recipe);
        P provider(TagManager tagManager, String namespace, String key, RE recipe);
    }
}
