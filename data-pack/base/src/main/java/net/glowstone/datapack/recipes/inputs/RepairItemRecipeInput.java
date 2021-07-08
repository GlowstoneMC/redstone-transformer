package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class RepairItemRecipeInput extends CraftingRecipeInput {
    public static RepairItemRecipeInputFactory factory() {
        return RepairItemRecipeInputFactory.getInstance();
    }

    private RepairItemRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class RepairItemRecipeInputFactory extends CraftingRecipeInputFactory<RepairItemRecipeInput> {
        private static volatile RepairItemRecipeInputFactory instance = null;

        private RepairItemRecipeInputFactory() {
            super(RepairItemRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + RepairItemRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static RepairItemRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (RepairItemRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new RepairItemRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
