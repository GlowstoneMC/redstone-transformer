package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class FireworkStarRecipeInput extends CraftingRecipeInput {
    public static FireworkStarRecipeInputFactory factory() {
        return FireworkStarRecipeInputFactory.getInstance();
    }

    private FireworkStarRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class FireworkStarRecipeInputFactory extends CraftingRecipeInputFactory<FireworkStarRecipeInput> {
        private static volatile FireworkStarRecipeInputFactory instance = null;

        private FireworkStarRecipeInputFactory() {
            super(FireworkStarRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + FireworkStarRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static FireworkStarRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (FireworkStarRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new FireworkStarRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
