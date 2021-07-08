package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class ArmorDyeRecipeInput extends CraftingRecipeInput {
    public static ArmorDyeRecipeInputFactory factory() {
        return ArmorDyeRecipeInputFactory.getInstance();
    }

    private ArmorDyeRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class ArmorDyeRecipeInputFactory extends CraftingRecipeInputFactory<ArmorDyeRecipeInput> {
        private static volatile ArmorDyeRecipeInputFactory instance = null;

        private ArmorDyeRecipeInputFactory() {
            super(ArmorDyeRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + ArmorDyeRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         private static ArmorDyeRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (ArmorDyeRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new ArmorDyeRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
