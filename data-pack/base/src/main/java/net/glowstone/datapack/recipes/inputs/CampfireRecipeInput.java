package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class CampfireRecipeInput implements RecipeInput {
    public static CampfireRecipeInputFactory factory() {
        return CampfireRecipeInputFactory.getInstance();
    }

    private final ItemStack input;

    private CampfireRecipeInput(ItemStack input) {
        this.input = input;
    }

    public ItemStack getInput() {
        return input;
    }

    private static class CampfireRecipeInputFactory implements SingleItemRecipeInputFactory<CampfireRecipeInput> {
        private static volatile CampfireRecipeInputFactory instance = null;

        private CampfireRecipeInputFactory() {
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + CampfireRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static CampfireRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (CampfireRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new CampfireRecipeInputFactory();
                    }
                }
            }
            return instance;
        }

        @Override
        public CampfireRecipeInput create(ItemStack itemStack) {
            return new CampfireRecipeInput(itemStack);
        }
    }
}
