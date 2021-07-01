package net.glowstone.datapack.recipes.providers;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.ShapedRecipe;
import net.glowstone.datapack.recipes.inputs.ShapedRecipeInput;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.CharacterMappingArgument;
import net.glowstone.datapack.utils.mapping.EnumMappingArgument;
import net.glowstone.datapack.utils.mapping.IntegerMappingArgument;
import net.glowstone.datapack.utils.mapping.ListMappingArgument;
import net.glowstone.datapack.utils.mapping.MapMappingArgument;
import net.glowstone.datapack.utils.mapping.OptionalMappingArgument;
import net.glowstone.datapack.utils.mapping.StringMappingArgument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;
import static net.glowstone.datapack.utils.ItemStackUtils.matchesWildcard;

public class ShapedRecipeProvider extends StaticRecipeProvider<ShapedRecipe, ShapedRecipeInput, org.bukkit.inventory.ShapedRecipe> {
    public static ShapedRecipeProviderFactory factory() {
        return ShapedRecipeProviderFactory.getInstance();
    }

    public ShapedRecipeProvider(org.bukkit.inventory.ShapedRecipe recipe) {
        super(recipe);
    }

    @Override
    public ShapedRecipeProviderFactory getFactory() {
        return factory();
    }

    public ShapedRecipeProvider setGroup(Optional<String> group) {
        group.ifPresent(getRecipe()::setGroup);
        return this;
    }

    public ShapedRecipeProvider setGroup(String group) {
        getRecipe().setGroup(group);
        return this;
    }

    public ShapedRecipeProvider setIngredients(Map<Character, RecipeChoice> ingredients) {
        ingredients.forEach(getRecipe()::setIngredient);
        return this;
    }

    public ShapedRecipeProvider setIngredient(char key, RecipeChoice ingredient) {
        getRecipe().setIngredient(key, ingredient);
        return this;
    }

    public ShapedRecipeProvider setShape(List<String> shape) {
        return setShape(shape.toArray(new String[0]));
    }

    public ShapedRecipeProvider setShape(String... shape) {
        getRecipe().shape(shape);
        return this;
    }

    @Override
    public Optional<org.bukkit.inventory.Recipe> getRecipeFor(ShapedRecipeInput input) {
        int size = (int) Math.sqrt(input.getInput().length);
        Map<Character, ItemStack> ingredients = getRecipe().getIngredientMap();
        String[] shape = getRecipe().getShape();

        int rows = shape.length;
        int cols = 0;
        for (String row : shape) {
            if (row.length() > cols) {
                cols = row.length();
            }
        }

        if (rows == 0 || cols == 0) {
            return Optional.empty();
        }

        // outer loop: try at each possible starting position
        for (int rowStart = 0; rowStart <= size - rows; ++rowStart) {
            for (int colStart = 0; colStart <= size - cols; ++colStart) {
                boolean skip = false;
                // inner loop: verify recipe against this position
                for (int row = 0; row < rows; ++row) {
                    for (int col = 0; col < cols; ++col) {
                        ItemStack given = input.getInput()[(rowStart + row) * size + colStart + col];
                        char ingredientChar =
                            shape[row].length() > col ? shape[row].charAt(col) : ' ';
                        ItemStack expected = ingredients.get(ingredientChar);

                        // check for mismatch in presence of an item in that slot at all
                        if (expected == null) {
                            if (itemStackIsEmpty(given)) {
                                skip = true;
                                break;
                            } else {
                                continue; // good match
                            }
                        } else if (itemStackIsEmpty(given)) {
                            skip = true;
                            break;
                        }

                        // check for type and data match
                        if (!matchesWildcard(expected, given)) {
                            skip = true;
                            break;
                        }
                    }

                    if (skip) {
                        break;
                    }
                }

                if (skip) {
                    continue;
                }

                // also check that no items outside the recipe size are present
                for (int row = 0; row < size; row++) {
                    for (int col = 0; col < size; col++) {
                        // if this position is outside the recipe and non-null, fail
                        if ((row < rowStart || row >= rowStart + rows || col < colStart
                            || col >= colStart + cols)
                            && itemStackIsEmpty(input.getInput()[row * size + col])) {
                            skip = true;
                            break;
                        }
                    }

                    if (skip) {
                        break;
                    }
                }

                if (skip) {
                    continue;
                }

                // recipe matches and zero items outside the recipe part.
                return Optional.of(getRecipe());
            }
        } // end position loop

        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRecipe().getKey(),
            getRecipe().getResult(),
            getRecipe().getChoiceMap(),
            Arrays.hashCode(getRecipe().getShape()),
            getRecipe().getGroup()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapedRecipeProvider that = (ShapedRecipeProvider) o;
        return Objects.equals(getRecipe().getKey(), that.getRecipe().getKey())
            && Objects.equals(getRecipe().getResult(), that.getRecipe().getResult())
            && Objects.equals(getRecipe().getChoiceMap(), that.getRecipe().getChoiceMap())
            && Arrays.equals(getRecipe().getShape(), that.getRecipe().getShape())
            && Objects.equals(getRecipe().getGroup(), that.getRecipe().getGroup());
    }

    public static class ShapedRecipeProviderFactory extends AbstractStaticRecipeProviderFactory<ShapedRecipeProvider, ShapedRecipe, ShapedRecipeInput, org.bukkit.inventory.ShapedRecipe> {
        private static volatile ShapedRecipeProviderFactory instance = null;

        private ShapedRecipeProviderFactory() {
            super(ShapedRecipeProvider.class, ShapedRecipe.class, ShapedRecipeInput.class, org.bukkit.inventory.ShapedRecipe.class);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ ShapedRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static ShapedRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (ShapedRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new ShapedRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }

        @Override
        public List<AbstractMappingArgument> providerArguments(String namespace, String key, ShapedRecipe recipe) {
            return ImmutableList.of(
                new StringMappingArgument(namespace),
                new StringMappingArgument(key),
                new EnumMappingArgument(Material.matchMaterial(recipe.getResult().getItem())),
                new IntegerMappingArgument(recipe.getResult().getCount()),
                new OptionalMappingArgument(recipe.getGroup().map(StringMappingArgument::new)),
                new ListMappingArgument(
                    recipe.getPattern()
                        .stream()
                        .map(StringMappingArgument::new)
                        .collect(Collectors.toList())
                ),
                new MapMappingArgument(
                    Character.class,
                    RecipeChoice.class,
                    recipe.getKey()
                        .entrySet()
                        .stream()
                        .map((entry) -> new AbstractMap.SimpleEntry<>(
                            new CharacterMappingArgument(entry.getKey()),
                            generateRecipeChoiceMapping(namespace, entry.getValue())
                        ))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                )
            );
        }

        @Override
        public ShapedRecipeProvider provider(TagManager tagManager, String namespace, String key, ShapedRecipe recipe) {
            return this.provider(
                namespace,
                key,
                Material.matchMaterial(recipe.getResult().getItem()),
                recipe.getResult().getCount(),
                recipe.getGroup(),
                recipe.getPattern(),
                recipe.getKey()
                    .entrySet()
                    .stream()
                    .map((entry) -> new AbstractMap.SimpleEntry<>(entry.getKey(), generateRecipeChoice(tagManager, namespace, entry.getValue())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        @Override
        public ShapedRecipeProvider provider(org.bukkit.inventory.ShapedRecipe recipe) {
            return new ShapedRecipeProvider(recipe);
        }

        public ShapedRecipeProvider provider(String namespace,
                                             String key,
                                             Material resultMaterial,
                                             int resultAmount,
                                             Optional<String> group,
                                             List<String> shape,
                                             Map<Character, RecipeChoice> ingredients) {
            org.bukkit.inventory.ShapedRecipe recipe = new org.bukkit.inventory.ShapedRecipe(
                new NamespacedKey(namespace, key),
                new ItemStack(resultMaterial, resultAmount)
            );
            group.ifPresent(recipe::setGroup);
            recipe.shape(shape.toArray(new String[0]));
            ingredients.forEach(recipe::setIngredient);
            return this.provider(recipe);
        }
    }
}
