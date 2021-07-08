package net.glowstone.datapack.recipes.providers;

import net.glowstone.datapack.loader.model.external.recipe.special.BookCloningRecipe;
import net.glowstone.datapack.recipes.StaticResultRecipe;
import net.glowstone.datapack.recipes.inputs.BookCloningRecipeInput;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Objects;
import java.util.Optional;

import static net.glowstone.datapack.utils.ItemStackUtils.itemStackIsEmpty;

public class BookCloningRecipeProvider extends SpecialRecipeProvider<BookCloningRecipe, BookCloningRecipeInput> {
    public static BookCloningRecipeProviderFactory factory() {
        return BookCloningRecipeProviderFactory.getInstance();
    }

    private BookCloningRecipeProvider(String namespace, String key) {
        super(new NamespacedKey(namespace, key));
    }

    @Override
    public BookCloningRecipeProviderFactory getFactory() {
        return factory();
    }

    @Override
    public Optional<Recipe> getRecipeFor(BookCloningRecipeInput input) {
        ItemStack original = null;
        int clones = 0;

        for (ItemStack item : input.getInput()) {
            if (itemStackIsEmpty(item)) {
                continue;
            }

            if (Material.WRITTEN_BOOK.equals(item.getType())) {
                if (original != null) {
                    return Optional.empty(); // Can't combine books
                }
                original = item;
                continue;
            }

            if (Material.WRITABLE_BOOK.equals(item.getType())) {
                clones++;
                continue;
            }

            return Optional.empty(); // Non-matching item
        }

        if (original == null) {
            return Optional.empty(); // No written book
        }

        if (clones == 0) {
            return Optional.empty(); // No writable books
        }

        ItemStack ret = original.clone();
        ret.setAmount(clones);

        return Optional.of(new StaticResultRecipe(getKey(), ret));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    public static class BookCloningRecipeProviderFactory extends AbstractSpecialRecipeProviderFactory<BookCloningRecipeProvider, BookCloningRecipe, BookCloningRecipeInput> {
        private static volatile BookCloningRecipeProviderFactory instance = null;

        private BookCloningRecipeProviderFactory() {
            super(BookCloningRecipeProvider.class, BookCloningRecipe.class, BookCloningRecipeInput.class, BookCloningRecipeProvider::new);
        	if (instance != null) {
        		throw new AssertionError(
        				"Another instance of "
        						+ BookCloningRecipeProviderFactory.class.getName()
        						+ " class already exists, Can't create a new instance.");
        	}
        }

         private static BookCloningRecipeProviderFactory getInstance() {
        	if (instance == null) {
        		synchronized (BookCloningRecipeProviderFactory.class) {
        			if (instance == null) {
        				instance = new BookCloningRecipeProviderFactory();
        			}
        		}
        	}
        	return instance;
        }
    }
}
