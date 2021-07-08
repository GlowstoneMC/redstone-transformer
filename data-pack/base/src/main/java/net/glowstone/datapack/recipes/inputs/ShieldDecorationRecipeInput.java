package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class ShieldDecorationRecipeInput extends CraftingRecipeInput {
    public static ShieldDecorationRecipeInputFactory factory() {
        return ShieldDecorationRecipeInputFactory.getInstance();
    }

    private ShieldDecorationRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class ShieldDecorationRecipeInputFactory extends CraftingRecipeInputFactory<ShieldDecorationRecipeInput> {
        private static volatile ShieldDecorationRecipeInputFactory instance = null;

        private ShieldDecorationRecipeInputFactory() {
            super(ShieldDecorationRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + ShieldDecorationRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static ShieldDecorationRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (ShieldDecorationRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new ShieldDecorationRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
