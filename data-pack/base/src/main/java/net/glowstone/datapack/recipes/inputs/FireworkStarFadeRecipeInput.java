package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class FireworkStarFadeRecipeInput extends CraftingRecipeInput {
    public static FireworkStarFadeRecipeInputFactory factory() {
        return FireworkStarFadeRecipeInputFactory.getInstance();
    }

    private FireworkStarFadeRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class FireworkStarFadeRecipeInputFactory extends CraftingRecipeInputFactory<FireworkStarFadeRecipeInput> {
        private static volatile FireworkStarFadeRecipeInputFactory instance = null;

        private FireworkStarFadeRecipeInputFactory() {
            super(FireworkStarFadeRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + FireworkStarFadeRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static FireworkStarFadeRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (FireworkStarFadeRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new FireworkStarFadeRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
