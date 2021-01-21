package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.CampfireCookingRecipe;
import net.glowstone.datapack.recipes.inputs.CampfireRecipeInput;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;
import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public class CampfireRecipeProvider extends StaticRecipeProvider<CampfireRecipe, CampfireRecipeInput> {
    public static CampfireRecipeProviderFactory factory() {
        return CampfireRecipeProviderFactory.getInstance();
    }

    private CampfireRecipeProvider(CampfireRecipe recipe) {
        super(CampfireRecipeInput.class, recipe);
    }

    @Override
    public Optional<Recipe> getRecipeFor(CampfireRecipeInput input) {
        ItemStack item = input.getInput();

        if (itemStackIsEmpty(item)) {
            return Optional.empty();
        }

        if (matchesWildcard(getRecipe().getInput(), item)) {
            return Optional.of(getRecipe());
        }

        return Optional.empty();
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
        CampfireRecipeProvider that = (CampfireRecipeProvider) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getInputChoice(), that.getRecipe().getInputChoice())
            && Objects.equals(getRecipe().getExperience(), that.getRecipe().getExperience())
            && Objects.equals(getRecipe().getCookingTime(), that.getRecipe().getCookingTime())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }

    public static class CampfireRecipeProviderFactory implements StaticRecipeProviderFactory<CampfireRecipeProvider, CampfireCookingRecipe, CampfireRecipe> {
        private static volatile CampfireRecipeProviderFactory instance = null;

        private CampfireRecipeProviderFactory() {
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ CampfireRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static CampfireRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (CampfireRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new CampfireRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }

        @Override
        public Class<CampfireCookingRecipe> getModelType() {
            return CampfireCookingRecipe.class;
        }

        @Override
        public Class<CampfireRecipe> getBukkitType() {
            return CampfireRecipe.class;
        }

        @Override
        public Class<CampfireRecipeProvider> getRecipeProviderType() {
            return CampfireRecipeProvider.class;
        }

        @Override
        public List<MappingArgument> providerArguments(String namespace, String key, CampfireCookingRecipe recipe) {
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
        public CampfireRecipeProvider provider(TagManager tagManager, String namespace, String key, CampfireCookingRecipe recipe) {
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
        public CampfireRecipeProvider provider(CampfireRecipe recipe) {
            return new CampfireRecipeProvider(recipe);
        }

        public CampfireRecipeProvider provider(String namespace,
                                               String key,
                                               Material resultMaterial,
                                               int resultAmount,
                                               Optional<String> group,
                                               RecipeChoice choice,
                                               float experience,
                                               int cookingTime) {
            CampfireRecipe recipe = new CampfireRecipe(
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
