package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class ShapelessRecipeInput extends CraftingRecipeInput {
    public static ShapelessRecipeInputFactory factory() {
        return ShapelessRecipeInputFactory.getInstance();
    }

    private ShapelessRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class ShapelessRecipeInputFactory extends CraftingRecipeInputFactory<ShapelessRecipeInput> {
        private static volatile ShapelessRecipeInputFactory instance = null;

        private ShapelessRecipeInputFactory() {
            super(ShapelessRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + ShapelessRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static ShapelessRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (ShapelessRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new ShapelessRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
