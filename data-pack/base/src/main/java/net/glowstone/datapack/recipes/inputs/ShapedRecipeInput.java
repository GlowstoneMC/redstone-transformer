package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class ShapedRecipeInput extends CraftingRecipeInput {
    public static ShapedRecipeInputFactory factory() {
        return ShapedRecipeInputFactory.getInstance();
    }

    private ShapedRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class ShapedRecipeInputFactory extends CraftingRecipeInputFactory<ShapedRecipeInput> {
        private static volatile ShapedRecipeInputFactory instance = null;

        private ShapedRecipeInputFactory() {
            super(ShapedRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + ShapedRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static ShapedRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (ShapedRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new ShapedRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
