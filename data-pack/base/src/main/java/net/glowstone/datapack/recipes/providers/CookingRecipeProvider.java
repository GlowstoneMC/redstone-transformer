package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.recipes.inputs.CookingRecipeInput;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public abstract class CookingRecipeProvider<T extends CookingRecipe<T>, I extends CookingRecipeInput> extends StaticRecipeProvider<T, I> {
    public CookingRecipeProvider(Class<I> inputClass, T recipe) {
        super(
            inputClass,
            recipe
        );
    }

    public CookingRecipeProvider<T, I> setGroup(Optional<String> group) {
        group.ifPresent(getRecipe()::setGroup);
        return this;
    }

    public CookingRecipeProvider<T, I> setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    @Override
    public NamespacedKey getKey() {
        return getRecipe().getKey();
    }

    @Override
    public Optional<Recipe> getRecipeFor(I input) {
        if (matchesWildcard(getRecipe().getInput(), input.getInput())) {
            return Optional.of(getRecipe());
        }

        return Optional.empty();
    }

    @FunctionalInterface
    public interface CookingRecipeConstructor<T extends CookingRecipe<T>> {
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
        CookingRecipeProvider<?, ?> genericThat = (CookingRecipeProvider<?, ?>) o;
        if (getRecipe().getClass() != genericThat.getRecipe().getClass()) return false;
        if (getInputClass() != genericThat.getInputClass()) return false;
        @SuppressWarnings("unchecked")
        CookingRecipeProvider<T, I> that = (CookingRecipeProvider<T, I>) genericThat;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getInputChoice(), that.getRecipe().getInputChoice())
            && Objects.equals(getRecipe().getExperience(), that.getRecipe().getExperience())
            && Objects.equals(getRecipe().getCookingTime(), that.getRecipe().getCookingTime())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }

    public abstract static class CookingRecipeProviderFactory<ExternalRecipe extends net.glowstone.datapack.loader.model.external.recipe.CookingRecipe,
                                                              BukkitRecipe extends CookingRecipe<BukkitRecipe>,
                                                              InputType extends CookingRecipeInput,
                                                              ProviderType extends CookingRecipeProvider<BukkitRecipe, InputType>>
                                                              implements StaticRecipeProviderFactory<ProviderType, ExternalRecipe, BukkitRecipe> {
        private final Class<ExternalRecipe> modelType;
        private final Class<ProviderType> recipeProvider;
        private final Class<BukkitRecipe> bukkitType;
        private final CookingRecipeConstructor<BukkitRecipe> bukkitConstructor;
        private final Function<BukkitRecipe, ProviderType> providerWrapper;

        public CookingRecipeProviderFactory(Class<ExternalRecipe> modelType,
                                            Class<ProviderType> recipeProvider,
                                            Class<BukkitRecipe> bukkitType,
                                            CookingRecipeConstructor<BukkitRecipe> bukkitConstructor,
                                            Function<BukkitRecipe, ProviderType> providerWrapper) {
            this.modelType = modelType;
            this.recipeProvider = recipeProvider;
            this.bukkitType = bukkitType;
            this.bukkitConstructor = bukkitConstructor;
            this.providerWrapper = providerWrapper;
        }

        @Override
        public Class<ExternalRecipe> getModelType() {
            return this.modelType;
        }

        @Override
        public Class<BukkitRecipe> getBukkitType() {
            return this.bukkitType;
        }

        @Override
        public Class<ProviderType> getRecipeProviderType() {
            return this.recipeProvider;
        }

        @Override
        public List<MappingArgument> providerArguments(String namespace, String key, ExternalRecipe recipe) {
            return ImmutableList.of(
                MappingArgument.forString(namespace),
                MappingArgument.forString(key),
                MappingArgument.forEnum(Material.matchMaterial(recipe.getResult())),
                MappingArgument.forInteger(1),
                MappingArgument.forOptional(recipe.getGroup().map(MappingArgument::forString)),
                RecipeProviderFactoryUtils.generateRecipeChoiceMapping(namespace, recipe.getIngredient()),
                MappingArgument.forFloat((float)recipe.getExperience()),
                MappingArgument.forInteger(recipe.getCookingTime())
            );
        }

        @Override
        public ProviderType provider(TagManager tagManager, String namespace, String key, ExternalRecipe recipe) {
            return this.provider(
                namespace,
                key,
                Material.matchMaterial(recipe.getResult()),
                1,
                recipe.getGroup(),
                RecipeProviderFactoryUtils.generateRecipeChoice(tagManager, namespace, recipe.getIngredient()),
                (float)recipe.getExperience(),
                recipe.getCookingTime()
            );
        }

        @Override
        public ProviderType provider(BukkitRecipe recipe) {
            return providerWrapper.apply(recipe);
        }

        public ProviderType provider(String namespace,
                                     String key,
                                     Material resultMaterial,
                                     int resultAmount,
                                     Optional<String> group,
                                     RecipeChoice choice,
                                     float experience,
                                     int cookingTime) {
            BukkitRecipe recipe = this.bukkitConstructor.create(
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
