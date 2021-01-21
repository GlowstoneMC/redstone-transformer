package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class SmokingRecipeInput extends CookingRecipeInput {
    public static SmokingRecipeInputFactory factory() {
        return SmokingRecipeInputFactory.getInstance();
    }

    private SmokingRecipeInput(ItemStack input) {
        super(input);
    }

    private static class SmokingRecipeInputFactory extends CookingRecipeInputFactory<SmokingRecipeInput> {
        private static volatile SmokingRecipeInputFactory instance = null;

        private SmokingRecipeInputFactory() {
            super(InventoryType.SMOKER, SmokingRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + SmokingRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static SmokingRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (SmokingRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new SmokingRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
