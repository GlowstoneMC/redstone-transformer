package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class ShulkerBoxColoringRecipeInput extends CraftingRecipeInput {
    public static ShulkerBoxColoringRecipeInputFactory factory() {
        return ShulkerBoxColoringRecipeInputFactory.getInstance();
    }

    private ShulkerBoxColoringRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class ShulkerBoxColoringRecipeInputFactory extends CraftingRecipeInputFactory<ShulkerBoxColoringRecipeInput> {
        private static volatile ShulkerBoxColoringRecipeInputFactory instance = null;

        private ShulkerBoxColoringRecipeInputFactory() {
            super(ShulkerBoxColoringRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + ShulkerBoxColoringRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static ShulkerBoxColoringRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (ShulkerBoxColoringRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new ShulkerBoxColoringRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
