package net.glowstone.datapack.recipes.inputs;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class BlastingRecipeInput extends CookingRecipeInput {
    public static BlastingRecipeInputFactory factory() {
        return BlastingRecipeInputFactory.getInstance();
    }

    private BlastingRecipeInput(ItemStack input) {
        super(input);
    }

    private static class BlastingRecipeInputFactory extends CookingRecipeInputFactory<BlastingRecipeInput> {
        private static volatile BlastingRecipeInputFactory instance = null;

        private BlastingRecipeInputFactory() {
            super(InventoryType.BLAST_FURNACE, BlastingRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + BlastingRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static BlastingRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (BlastingRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new BlastingRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
