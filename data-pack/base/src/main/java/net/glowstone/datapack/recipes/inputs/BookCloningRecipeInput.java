package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class BookCloningRecipeInput extends CraftingRecipeInput {
    public static BookCloningRecipeInputFactory factory() {
        return BookCloningRecipeInputFactory.getInstance();
    }

    private BookCloningRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class BookCloningRecipeInputFactory extends CraftingRecipeInputFactory<BookCloningRecipeInput> {
        private static volatile BookCloningRecipeInputFactory instance = null;

        private BookCloningRecipeInputFactory() {
            super(BookCloningRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + BookCloningRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static BookCloningRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (BookCloningRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new BookCloningRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
