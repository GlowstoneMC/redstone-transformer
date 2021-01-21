package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class FurnaceRecipeInput extends CookingRecipeInput {
    public static FurnaceRecipeInputFactory factory() {
        return FurnaceRecipeInputFactory.getInstance();
    }

    private FurnaceRecipeInput(ItemStack input) {
        super(input);
    }

    private static class FurnaceRecipeInputFactory extends CookingRecipeInputFactory<FurnaceRecipeInput> {
        private static volatile FurnaceRecipeInputFactory instance = null;

        private FurnaceRecipeInputFactory() {
            super(InventoryType.FURNACE, FurnaceRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + FurnaceRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static FurnaceRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (FurnaceRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new FurnaceRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
