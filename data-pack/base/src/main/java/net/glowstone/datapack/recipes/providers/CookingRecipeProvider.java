package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.CookingRecipe;
import net.glowstone.datapack.recipes.inputs.CookingRecipeInput;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.EnumMappingArgument;
import net.glowstone.datapack.utils.mapping.FloatMappingArgument;
import net.glowstone.datapack.utils.mapping.IntegerMappingArgument;
import net.glowstone.datapack.utils.mapping.OptionalMappingArgument;
import net.glowstone.datapack.utils.mapping.StringMappingArgument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public abstract class CookingRecipeProvider<RE extends CookingRecipe, RI extends CookingRecipeInput, RB extends org.bukkit.inventory.CookingRecipe<RB>> extends StaticRecipeProvider<RE, RI, RB> {
    public CookingRecipeProvider(RB recipe) {
        super(recipe);
    }

    public CookingRecipeProvider<RE, RI, RB> setGroup(Optional<String> group) {
        group.ifPresent(getRecipe()::setGroup);
        return this;
    }

    public CookingRecipeProvider<RE, RI, RB> setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    @Override
    public NamespacedKey getKey() {
        return getRecipe().getKey();
    }

    @Override
    public Optional<Recipe> getRecipeFor(RI input) {
        if (matchesWildcard(getRecipe().getInput(), input.getInput())) {
            return Optional.of(getRecipe());
        }

        return Optional.empty();
    }

    @FunctionalInterface
    public interface CookingRecipeConstructor<T extends org.bukkit.inventory.CookingRecipe<T>> {
        T create(NamespacedKey key, ItemStack result, RecipeChoice input, float experience, int cookingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRecipe().getKey(),
            getRecipe().getResult(),
            getRecipe().getInputChoice(),
            getRecipe().getExperience(),
            getRecipe().getCookingTime(),
            getRecipe().getGroup()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        @SuppressWarnings("unchecked")
        CookingRecipeProvider<RE, RI, RB> that = (CookingRecipeProvider<RE, RI, RB>) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getInputChoice(), that.getRecipe().getInputChoice())
            && Objects.equals(getRecipe().getExperience(), that.getRecipe().getExperience())
            && Objects.equals(getRecipe().getCookingTime(), that.getRecipe().getCookingTime())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }

    public abstract static class CookingRecipeProviderFactory<P extends CookingRecipeProvider<RE, RI, RB>,
                                                              RE extends CookingRecipe,
                                                              RI extends CookingRecipeInput,
                                                              RB extends org.bukkit.inventory.CookingRecipe<RB>>
                                                              extends AbstractStaticRecipeProviderFactory<P, RE, RI, RB> {
        private final CookingRecipeConstructor<RB> bukkitConstructor;
        private final Function<RB, P> providerWrapper;

        public CookingRecipeProviderFactory(Class<P> recipeProviderType,
                                            Class<RE> modelType,
                                            Class<RI> inputType,
                                            Class<RB> bukkitType,
                                            CookingRecipeConstructor<RB> bukkitConstructor,
                                            Function<RB, P> providerWrapper) {
            super(recipeProviderType, modelType, inputType, bukkitType);
            this.bukkitConstructor = bukkitConstructor;
            this.providerWrapper = providerWrapper;
        }

        @Override
        public List<AbstractMappingArgument> providerArguments(String namespace, String key, RE recipe) {
            return ImmutableList.of(
                new StringMappingArgument(namespace),
                new StringMappingArgument(key),
                new EnumMappingArgument(Material.matchMaterial(recipe.getResult())),
                new IntegerMappingArgument(1),
                new OptionalMappingArgument(recipe.getGroup().map(StringMappingArgument::new)),
                generateRecipeChoiceMapping(namespace, recipe.getIngredient()),
                new FloatMappingArgument((float)recipe.getExperience()),
                new IntegerMappingArgument(recipe.getCookingTime())
            );
        }

        @Override
        public P provider(TagManager tagManager, String namespace, String key, RE recipe) {
            return this.provider(
                namespace,
                key,
                Material.matchMaterial(recipe.getResult()),
                1,
                recipe.getGroup(),
                generateRecipeChoice(tagManager, namespace, recipe.getIngredient()),
                (float)recipe.getExperience(),
                recipe.getCookingTime()
            );
        }

        @Override
        public P provider(RB recipe) {
            return providerWrapper.apply(recipe);
        }

        public P provider(String namespace,
                          String key,
                          Material resultMaterial,
                          int resultAmount,
                          Optional<String> group,
                          RecipeChoice choice,
                          float experience,
                          int cookingTime) {
            RB recipe = this.bukkitConstructor.create(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                choice,
                experience,
                cookingTime
            );
            group.ifPresent(recipe::setGroup);
            return this.provider(recipe);
        }
    }
}
