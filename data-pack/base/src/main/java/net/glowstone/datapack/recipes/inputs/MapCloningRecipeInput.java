package net.glowstone.datapack.recipes.inputs;

import org.bukkit.inventory.ItemStack;

public class MapCloningRecipeInput extends CraftingRecipeInput {
    public static MapCloningRecipeInputFactory factory() {
        return MapCloningRecipeInputFactory.getInstance();
    }

    private MapCloningRecipeInput(ItemStack[] input) {
        super(input);
    }

    private static class MapCloningRecipeInputFactory extends CraftingRecipeInputFactory<MapCloningRecipeInput> {
        private static volatile MapCloningRecipeInputFactory instance = null;

        private MapCloningRecipeInputFactory() {
            super(MapCloningRecipeInput::new);
            if (instance != null) {
                throw new AssertionError(
                        "Another instance of "
                                + MapCloningRecipeInputFactory.class.getName()
                                + " class already exists, Can't create a new instance.");
            }
        }

         public static MapCloningRecipeInputFactory getInstance() {
            if (instance == null) {
                synchronized (MapCloningRecipeInputFactory.class) {
                    if (instance == null) {
                        instance = new MapCloningRecipeInputFactory();
                    }
                }
            }
            return instance;
        }
    }
}
