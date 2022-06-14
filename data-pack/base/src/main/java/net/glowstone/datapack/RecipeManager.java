package net.glowstone.datapack;

import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.recipes.providers.RecipeProvider;
import net.glowstone.datapack.recipes.inputs.RecipeInput;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecipeManager {
    Set<NamespacedKey> getAllRecipeKeys();

    Optional<RecipeProvider<?, ?>> getRecipeProvider(NamespacedKey key);

    /**
     * Gets the recipe associated with the given inventory object. This could return any type of recipe - shaped,
     * shapeless, or special. Make sure to check the type before using.
     * @param inventory The inventory object, containing all ingredients that are needed to create a recipe.
     * @return The recipe object, or null if no recipe was found.
     */
    Recipe getRecipe(Inventory inventory);

    Recipe getRecipe(InventoryType inventoryType, ItemStack[] itemStacks);

    Recipe getRecipe(RecipeInput input);

    Recipe getRecipe(NamespacedKey key);

    List<Recipe> getAllRecipesForResult(ItemStack result);

    Iterator<Recipe> getAllRecipes();

    boolean addRecipe(Recipe recipe);

    boolean removeRecipe(NamespacedKey key);

    /**
     * Loads all recipes from the given data pack.
     * @param dataPack The data pack object, successfully loaded using
     * {@link net.glowstone.datapack.loader.DataPackLoader#loadPack(Path)}.
     */
    void loadFromDataPack(DataPack dataPack);

    /**
     * Resets the recipes within this manager to their default states.
     */
    void resetToDefaults();

    void clearRecipes();
}
