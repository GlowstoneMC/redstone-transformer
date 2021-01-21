package net.glowstone.datapack.recipes.providers;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.SmithingRecipe;
import net.glowstone.datapack.recipes.inputs.SmithingRecipeInput;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class SmithingRecipeProvider extends StaticRecipeProvider<org.bukkit.inventory.SmithingRecipe, SmithingRecipeInput> {
    public static SmithingRecipeProviderFactory factory() {
        return SmithingRecipeProviderFactory.getInstance();
    }

    public SmithingRecipeProvider(String namespace, String key, Material resultMaterial, int resultAmount, RecipeChoice equipment, RecipeChoice mineral) {
        super(
            SmithingRecipeInput.class,
            new org.bukkit.inventory.SmithingRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                equipment,
                mineral
            )
        );
    }

    public SmithingRecipeProvider(org.bukkit.inventory.SmithingRecipe recipe) {
        super(SmithingRecipeInput.class, recipe);
    }

    @Override
    public Optional<Recipe> getRecipeFor(SmithingRecipeInput input) {
        ItemStack equipment = input.getInputEquipment();
        ItemStack mineral = input.getInputMineral();

        if (itemStackIsEmpty(equipment) || itemStackIsEmpty(mineral)) {
            return Optional.empty();
        }

        if (getRecipe().getBase().test(equipment) && getRecipe().getAddition().test(mineral)) {
            return Optional.of(getRecipe());
        }

        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRecipe().getKey(),
            getRecipe().getResult(),
            getRecipe().getBase(),
            getRecipe().getAddition()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmithingRecipeProvider that = (SmithingRecipeProvider) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getBase(), that.getRecipe().getBase())
            && Objects.equals(getRecipe().getAddition(), that.getRecipe().getAddition());
    }

    public static class SmithingRecipeProviderFactory implements StaticRecipeProviderFactory<SmithingRecipeProvider, SmithingRecipe, org.bukkit.inventory.SmithingRecipe> {
        private static volatile SmithingRecipeProviderFactory instance = null;

        private SmithingRecipeProviderFactory() {
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ SmithingRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static SmithingRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (SmithingRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new SmithingRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }

        @Override
        public Class<SmithingRecipe> getModelType() {
            return SmithingRecipe.class;
        }

        @Override
        public Class<org.bukkit.inventory.SmithingRecipe> getBukkitType() {
            return org.bukkit.inventory.SmithingRecipe.class;
        }

        @Override
        public Class<SmithingRecipeProvider> getRecipeProviderType() {
            return SmithingRecipeProvider.class;
        }

        @Override
        public List<MappingArgument> providerArguments(String namespace, String key, SmithingRecipe recipe) {
            Preconditions.checkArgument(
                recipe.getResult().getItem().isPresent(),
                "SmithingRecipe must result in an item."
            );

            return ImmutableList.of(
                MappingArgument.forString(namespace),
                MappingArgument.forString(key),
                MappingArgument.forEnum(Material.matchMaterial(recipe.getResult().getItem().get())),
                MappingArgument.forInteger(1),
                RecipeProviderFactoryUtils.generateRecipeChoiceMapping(namespace, Collections.singletonList(recipe.getBase())),
                RecipeProviderFactoryUtils.generateRecipeChoiceMapping(namespace, Collections.singletonList(recipe.getAddition()))
            );
        }

        @Override
        public SmithingRecipeProvider provider(TagManager tagManager, String namespace, String key, SmithingRecipe recipe) {
            Preconditions.checkArgument(
                recipe.getResult().getItem().isPresent(),
                "SmithingRecipe must result in an item."
            );

            return this.provider(
                namespace,
                key,
                Material.matchMaterial(recipe.getResult().getItem().get()),
                1,
                RecipeProviderFactoryUtils.generateRecipeChoice(tagManager, namespace, Collections.singletonList(recipe.getBase())),
                RecipeProviderFactoryUtils.generateRecipeChoice(tagManager, namespace, Collections.singletonList(recipe.getAddition()))
            );
        }

        @Override
        public SmithingRecipeProvider provider(org.bukkit.inventory.SmithingRecipe recipe) {
            return new SmithingRecipeProvider(recipe);
        }

        public SmithingRecipeProvider provider(String namespace,
                                               String key,
                                               Material resultMaterial,
                                               int resultAmount,
                                               RecipeChoice equipment,
                                               RecipeChoice mineral) {
            org.bukkit.inventory.SmithingRecipe recipe = new org.bukkit.inventory.SmithingRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount),
                equipment,
                mineral
            );
            return this.provider(recipe);
        }
    }
}
