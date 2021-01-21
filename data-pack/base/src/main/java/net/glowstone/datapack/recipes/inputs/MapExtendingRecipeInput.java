package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class MapExtendingRecipeInput extends CraftingRecipeInput {
    public static MapExtendingRecipeInputFactory factory() {
        return MapExtendingRecipeInputFactory.getInstance();
    }

    private MapExtendingRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class MapExtendingRecipeInputFactory extends CraftingRecipeInputFactory<MapExtendingRecipeInput> {
        private static volatile MapExtendingRecipeInputFactory instance = null;

        private MapExtendingRecipeInputFactory() {
            super(MapExtendingRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + MapExtendingRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static MapExtendingRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (MapExtendingRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new MapExtendingRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
