package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class TippedArrowRecipeInput extends CraftingRecipeInput {
    public static TippedArrowRecipeInputFactory factory() {
        return TippedArrowRecipeInputFactory.getInstance();
    }

    private TippedArrowRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class TippedArrowRecipeInputFactory extends CraftingRecipeInputFactory<TippedArrowRecipeInput> {
        private static volatile TippedArrowRecipeInputFactory instance = null;

        private TippedArrowRecipeInputFactory() {
            super(TippedArrowRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + TippedArrowRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static TippedArrowRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (TippedArrowRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new TippedArrowRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
