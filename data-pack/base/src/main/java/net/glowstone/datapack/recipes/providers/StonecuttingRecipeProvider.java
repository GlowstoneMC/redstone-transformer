package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.StonecuttingRecipe;
import net.glowstone.datapack.recipes.inputs.StonecuttingRecipeInput;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.EnumMappingArgument;
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

import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public class StonecuttingRecipeProvider extends StaticRecipeProvider<StonecuttingRecipe, StonecuttingRecipeInput, org.bukkit.inventory.StonecuttingRecipe> {
    public static StonecuttingRecipeProviderFactory factory() {
        return StonecuttingRecipeProviderFactory.getInstance();
    }

    public StonecuttingRecipeProvider(org.bukkit.inventory.StonecuttingRecipe recipe) {
        super(recipe);
    }

    @Override
    public StonecuttingRecipeProviderFactory getFactory() {
        return factory();
    }

    public StonecuttingRecipeProvider setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    @Override
    public Optional<Recipe> getRecipeFor(StonecuttingRecipeInput input) {
        if (matchesWildcard(getRecipe().getInput(), input.getInput())) {
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
            getRecipe().getGroup()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StonecuttingRecipeProvider that = (StonecuttingRecipeProvider) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getInputChoice(), that.getRecipe().getInputChoice())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }

    public static class StonecuttingRecipeProviderFactory extends AbstractStaticRecipeProviderFactory<StonecuttingRecipeProvider, StonecuttingRecipe, StonecuttingRecipeInput, org.bukkit.inventory.StonecuttingRecipe> {
        private static volatile StonecuttingRecipeProviderFactory instance = null;

        private StonecuttingRecipeProviderFactory() {
            super(StonecuttingRecipeProvider.class, StonecuttingRecipe.class, StonecuttingRecipeInput.class, org.bukkit.inventory.StonecuttingRecipe.class);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ StonecuttingRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static StonecuttingRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (StonecuttingRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new StonecuttingRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }

        @Override
        public List<AbstractMappingArgument> providerArguments(String namespace, String key, StonecuttingRecipe recipe) {
            return ImmutableList.of(
                new StringMappingArgument(namespace),
                new StringMappingArgument(key),
                new EnumMappingArgument(Material.matchMaterial(recipe.getResult())),
                new IntegerMappingArgument(1),
                new OptionalMappingArgument(recipe.getGroup().map(StringMappingArgument::new)),
                generateRecipeChoiceMapping(namespace, recipe.getIngredient())
            );
        }

        @Override
        public StonecuttingRecipeProvider provider(TagManager tagManager, String namespace, String key, StonecuttingRecipe recipe) {
            return this.provider(
                namespace,
                key,
                Material.matchMaterial(recipe.getResult()),
                1,
                recipe.getGroup(),
                generateRecipeChoice(tagManager, namespace, recipe.getIngredient())
            );
        }

        @Override
        public StonecuttingRecipeProvider provider(org.bukkit.inventory.StonecuttingRecipe recipe) {
            return new StonecuttingRecipeProvider(recipe);
        }

        public StonecuttingRecipeProvider provider(String namespace,
                                                   String key,
                                                   Material resultMaterial,
                                                   int resultAmount,
                                                   Optional<String> group,
                                                   RecipeChoice source) {
            org.bukkit.inventory.StonecuttingRecipe recipe = new org.bukkit.inventory.StonecuttingRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                source
            );
            group.ifPresent(recipe::setGroup);
            return this.provider(recipe);
        }
    }
}
