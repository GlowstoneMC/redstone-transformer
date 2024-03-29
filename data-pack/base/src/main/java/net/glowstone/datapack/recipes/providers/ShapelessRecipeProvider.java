package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.ShapelessRecipe;
import net.glowstone.datapack.recipes.MaterialTagRecipeChoice;
import net.glowstone.datapack.recipes.inputs.ShapelessRecipeInput;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.EnumMappingArgument;
import net.glowstone.datapack.utils.mapping.IntegerMappingArgument;
import net.glowstone.datapack.utils.mapping.ListMappingArgument;
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
import java.util.stream.Collectors;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;
import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public class ShapelessRecipeProvider extends StaticRecipeProvider<ShapelessRecipe, ShapelessRecipeInput, org.bukkit.inventory.ShapelessRecipe> {
    public static ShapelessRecipeProviderFactory factory() {
        return ShapelessRecipeProviderFactory.getInstance();
    }

    public ShapelessRecipeProvider(org.bukkit.inventory.ShapelessRecipe recipe) {
        super(recipe);
    }

    @Override
    public ShapelessRecipeProviderFactory getFactory() {
        return factory();
    }

    public ShapelessRecipeProvider setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    public ShapelessRecipeProvider addIngredient(MaterialTagRecipeChoice choice) {
        getRecipe().addIngredient(choice);
        return this;
    }

    @Override
    public Optional<Recipe> getRecipeFor(ShapelessRecipeInput input) {
        boolean[] accountedFor = new boolean[input.getInput().length];

        // Mark empty item slots accounted for
        for (int i = 0; i < input.getInput().length; ++i) {
            accountedFor[i] = itemStackIsEmpty(input.getInput()[i]);
        }

        // Make sure each ingredient in the recipe exists in the inventory
        for (ItemStack ingredient : getRecipe().getIngredientList()) {
            boolean foundItem = false;
            for (int i = 0; i < input.getInput().length; ++i) {
                // if this item is not already used and it matches this ingredient...
                if (!accountedFor[i] && matchesWildcard(ingredient, input.getInput()[i])) {
                    // ... this item is accounted for and this ingredient is found.
                    accountedFor[i] = foundItem = true;
                }
            }
            // no item matched this ingredient, so the recipe fails
            if (!foundItem) {
                return Optional.empty();
            }
        }

        // Make sure inventory has no leftover items
        for (int i = 0; i < input.getInput().length; ++i) {
            if (!accountedFor[i]) {
                return Optional.empty();
            }
        }

        return Optional.of(getRecipe());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRecipe().getKey(),
            getRecipe().getResult(),
            getRecipe().getChoiceList(),
            getRecipe().getGroup()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapelessRecipeProvider that = (ShapelessRecipeProvider) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getChoiceList(), that.getRecipe().getChoiceList())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }

    public static class ShapelessRecipeProviderFactory extends AbstractStaticRecipeProviderFactory<ShapelessRecipeProvider, ShapelessRecipe, ShapelessRecipeInput, org.bukkit.inventory.ShapelessRecipe> {
        private static volatile ShapelessRecipeProviderFactory instance = null;

        private ShapelessRecipeProviderFactory() {
            super(ShapelessRecipeProvider.class, ShapelessRecipe.class, ShapelessRecipeInput.class, org.bukkit.inventory.ShapelessRecipe.class);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ ShapelessRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static ShapelessRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (ShapelessRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new ShapelessRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }

        @Override
        public List<AbstractMappingArgument> providerArguments(String namespace, String key, ShapelessRecipe recipe) {
            return ImmutableList.of(
                new StringMappingArgument(namespace),
                new StringMappingArgument(key),
                new EnumMappingArgument(Material.matchMaterial(recipe.getResult().getItem())),
                new IntegerMappingArgument(recipe.getResult().getCount()),
                new OptionalMappingArgument(recipe.getGroup().map(StringMappingArgument::new)),
                new ListMappingArgument(
                    recipe.getIngredients()
                        .stream()
                        .map((items) -> generateRecipeChoiceMapping(namespace, items))
                        .collect(Collectors.toList())
                )
            );
        }

        @Override
        public ShapelessRecipeProvider provider(TagManager tagManager, String namespace, String key, ShapelessRecipe recipe) {
            return this.provider(
                namespace,
                key,
                Material.matchMaterial(recipe.getResult().getItem()),
                recipe.getResult().getCount(),
                recipe.getGroup(),
                recipe.getIngredients()
                    .stream()
                    .map((items) -> generateRecipeChoice(tagManager, namespace, items))
                    .collect(Collectors.toList())
            );
        }

        @Override
        public ShapelessRecipeProvider provider(org.bukkit.inventory.ShapelessRecipe recipe) {
            return new ShapelessRecipeProvider(recipe);
        }

        public ShapelessRecipeProvider provider(String namespace,
                                                String key,
                                                Material resultMaterial,
                                                int resultAmount,
                                                Optional<String> group,
                                                List<RecipeChoice> ingredients) {
            org.bukkit.inventory.ShapelessRecipe recipe = new org.bukkit.inventory.ShapelessRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount)
            );
            group.ifPresent(recipe::setGroup);
            ingredients.forEach(recipe::addIngredient);
            return this.provider(recipe);
        }
    }
}
