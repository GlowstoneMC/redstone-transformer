package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class BannerDuplicateRecipeInput extends CraftingRecipeInput {
    public static BannerDuplicateRecipeInputFactory factory() {
        return BannerDuplicateRecipeInputFactory.getInstance();
    }

    private BannerDuplicateRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class BannerDuplicateRecipeInputFactory extends CraftingRecipeInputFactory<BannerDuplicateRecipeInput> {
        private static volatile BannerDuplicateRecipeInputFactory instance = null;

        private BannerDuplicateRecipeInputFactory() {
            super(BannerDuplicateRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + BannerDuplicateRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static BannerDuplicateRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (BannerDuplicateRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new BannerDuplicateRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
