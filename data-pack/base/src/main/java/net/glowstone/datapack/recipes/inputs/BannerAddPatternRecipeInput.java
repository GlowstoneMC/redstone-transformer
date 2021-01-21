package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class BannerAddPatternRecipeInput extends CraftingRecipeInput {
    public static BannerAddPatternRecipeInputFactory factory() {
        return BannerAddPatternRecipeInputFactory.getInstance();
    }

    private BannerAddPatternRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class BannerAddPatternRecipeInputFactory extends CraftingRecipeInputFactory<BannerAddPatternRecipeInput> {
        private static volatile BannerAddPatternRecipeInputFactory instance = null;

        private BannerAddPatternRecipeInputFactory() {
            super(BannerAddPatternRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + BannerAddPatternRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static BannerAddPatternRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (BannerAddPatternRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new BannerAddPatternRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
