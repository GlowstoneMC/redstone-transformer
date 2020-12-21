package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.CookingRecipe;
import net.glowstone.datapack.recipes.providers.CookingRecipeProvider;
import net.glowstone.datapack.recipes.providers.CookingRecipeProvider.CookingRecipeConstructor;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;

import java.util.List;
import java.util.Optional;

public class CookingRecipeProviderMapping<ExternalRecipe extends CookingRecipe, BukkkitRecipe extends org.bukkit.inventory.CookingRecipe<BukkkitRecipe>, ProviderType extends CookingRecipeProvider<BukkkitRecipe>> implements RecipeProviderMapping<ProviderType, ExternalRecipe> {
    private final Class<ExternalRecipe> modelType;
    private final Class<ProviderType> recipeProvider;
    private final Class<BukkkitRecipe> bukkitType;
    private final CookingRecipeConstructor<BukkkitRecipe> bukkitConstructor;
    private final CookingRecipeProviderConstructor<BukkkitRecipe, ProviderType> providerConstructor;

    public CookingRecipeProviderMapping(Class<ExternalRecipe> modelType,
                                        Class<ProviderType> recipeProvider,
                                        Class<BukkkitRecipe> bukkitType,
                                        CookingRecipeConstructor<BukkkitRecipe> bukkitConstructor,
                                        CookingRecipeProviderConstructor<BukkkitRecipe, ProviderType> providerConstructor) {
        this.modelType = modelType;
        this.recipeProvider = recipeProvider;
        this.bukkitType = bukkitType;
        this.bukkitConstructor = bukkitConstructor;
        this.providerConstructor = providerConstructor;
    }

    @Override
    public Class<ExternalRecipe> getModelType() {
        return this.modelType;
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
            MappingUtils.generateRecipeChoiceMapping(namespace, recipe.getIngredient()),
            MappingArgument.forFloat((float)recipe.getExperience()),
            MappingArgument.forInteger(recipe.getCookingTime()),
            MappingArgument.forMethodReference(this.bukkitType, "new")
        );
    }

    @Override
    public ProviderType provider(TagManager tagManager, String namespace, String key, ExternalRecipe recipe) {
        return providerConstructor.create(
            namespace,
            key,
            Material.matchMaterial(recipe.getResult()),
            1,
            recipe.getGroup(),
            MappingUtils.generateRecipeChoice(tagManager, namespace, recipe.getIngredient()),
            (float)recipe.getExperience(),
            recipe.getCookingTime(),
            this.bukkitConstructor
        );
    }

    public interface CookingRecipeProviderConstructor<BukkkitRecipe extends org.bukkit.inventory.CookingRecipe<BukkkitRecipe>, ProviderType extends CookingRecipeProvider<BukkkitRecipe>> {
        ProviderType create(String namespace,
                            String key,
                            Material resultMaterial,
                            int resultAmount,
                            Optional<String> group,
                            RecipeChoice choice,
                            float experience,
                            int cookingTime,
                            CookingRecipeConstructor<BukkkitRecipe> constructor);
    }
}
