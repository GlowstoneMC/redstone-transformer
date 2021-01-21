package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class StonecuttingRecipeInput extends CookingRecipeInput {
    public static StonecuttingRecipeInputFactory factory() {
        return StonecuttingRecipeInputFactory.getInstance();
    }

    private StonecuttingRecipeInput(ItemStack input) {
        super(input);
    }

    private static class StonecuttingRecipeInputFactory extends CookingRecipeInputFactory<StonecuttingRecipeInput> {
        private static volatile StonecuttingRecipeInputFactory instance = null;

        private StonecuttingRecipeInputFactory() {
            super(InventoryType.STONECUTTER, StonecuttingRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + StonecuttingRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static StonecuttingRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (StonecuttingRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new StonecuttingRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
