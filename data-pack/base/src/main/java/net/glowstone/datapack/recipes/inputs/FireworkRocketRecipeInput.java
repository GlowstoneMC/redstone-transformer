package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class FireworkRocketRecipeInput extends CraftingRecipeInput {
    public static FireworkRocketRecipeInputFactory factory() {
        return FireworkRocketRecipeInputFactory.getInstance();
    }

    private FireworkRocketRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class FireworkRocketRecipeInputFactory extends CraftingRecipeInputFactory<FireworkRocketRecipeInput> {
        private static volatile FireworkRocketRecipeInputFactory instance = null;

        private FireworkRocketRecipeInputFactory() {
            super(FireworkRocketRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + FireworkRocketRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static FireworkRocketRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (FireworkRocketRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new FireworkRocketRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
