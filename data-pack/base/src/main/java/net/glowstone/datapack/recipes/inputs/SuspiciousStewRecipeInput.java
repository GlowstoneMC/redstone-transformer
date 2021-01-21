package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class SuspiciousStewRecipeInput extends CraftingRecipeInput {
    public static SuspiciousStewRecipeInputFactory factory() {
        return SuspiciousStewRecipeInputFactory.getInstance();
    }

    private SuspiciousStewRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class SuspiciousStewRecipeInputFactory extends CraftingRecipeInputFactory<SuspiciousStewRecipeInput> {
        private static volatile SuspiciousStewRecipeInputFactory instance = null;

        private SuspiciousStewRecipeInputFactory() {
            super(SuspiciousStewRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + SuspiciousStewRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static SuspiciousStewRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (SuspiciousStewRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new SuspiciousStewRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
