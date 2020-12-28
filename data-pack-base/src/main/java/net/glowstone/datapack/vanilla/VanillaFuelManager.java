package net.glowstone.datapack.vanilla;

import com.destroystokyo.paper.MaterialTags;
import net.glowstone.datapack.AbstractFuelManager;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.tags.ExtraMaterialTags;
import org.bukkit.Material;
import org.bukkit.Tag;

public class VanillaFuelManager extends AbstractFuelManager {
    public VanillaFuelManager(TagManager tagManager) {
        super(tagManager);
    }

    @Override
    protected Fuels defaultFuels() {
        Fuels fuels = new Fuels();

        fuels.addTaggedFuel(Tag.BANNERS.getKey(), 300);
        fuels.addSingleFuel(Material.BLAZE_ROD, 2400);
        fuels.addSingleFuel(Material.COAL_BLOCK, 16000);
        fuels.addTaggedFuel(Tag.ITEMS_BOATS.getKey(), 400);
        fuels.addSingleFuel(Material.BOOKSHELF, 300);
        fuels.addSingleFuel(Material.BOW, 300);
        fuels.addSingleFuel(Material.BOWL, 100);
        fuels.addTaggedFuel(Tag.CARPETS.getKey(), 67);
        fuels.addSingleFuel(Material.COAL, 1600);
        fuels.addSingleFuel(Material.CHEST, 300);
        fuels.addSingleFuel(Material.CRAFTING_TABLE, 300);
        fuels.addSingleFuel(Material.DAYLIGHT_DETECTOR, 300);
        fuels.addTaggedFuel(Tag.WOODEN_FENCES.getKey(), 300);
        fuels.addTaggedFuel(MaterialTags.WOODEN_GATES.getKey(), 300);
        fuels.addSingleFuel(Material.FISHING_ROD, 300);
        fuels.addSingleFuel(Material.JUKEBOX, 300);
        fuels.addSingleFuel(Material.LADDER, 300);
        fuels.addSingleFuel(Material.LAVA_BUCKET, 20000);
        fuels.addSingleFuel(Material.BROWN_MUSHROOM_BLOCK, 300);
        fuels.addSingleFuel(Material.RED_MUSHROOM_BLOCK, 300);
        fuels.addSingleFuel(Material.NOTE_BLOCK, 300);
        fuels.addTaggedFuel(Tag.SAPLINGS.getKey(), 100);
        fuels.addTaggedFuel(MaterialTags.SIGNS.getKey(), 200);
        fuels.addSingleFuel(Material.STICK, 100);
        fuels.addTaggedFuel(Tag.TRAPDOORS.getKey(), 300);
        fuels.addSingleFuel(Material.TRAPPED_CHEST, 300);
        fuels.addTaggedFuel(Tag.LOGS.getKey(), 300);
        fuels.addTaggedFuel(ExtraMaterialTags.WOODS.getKey(), 300);
        fuels.addTaggedFuel(Tag.PLANKS.getKey(), 300);
        fuels.addTaggedFuel(Tag.WOODEN_BUTTONS.getKey(), 300);
        fuels.addTaggedFuel(Tag.WOODEN_PRESSURE_PLATES.getKey(), 300);
        fuels.addTaggedFuel(Tag.WOODEN_STAIRS.getKey(), 300);
        fuels.addSingleFuel(Material.WOODEN_AXE, 200);
        fuels.addSingleFuel(Material.WOODEN_HOE, 200);
        fuels.addSingleFuel(Material.WOODEN_PICKAXE, 200);
        fuels.addSingleFuel(Material.WOODEN_SHOVEL, 200);
        fuels.addSingleFuel(Material.WOODEN_SWORD, 200);
        fuels.addTaggedFuel(Tag.WOODEN_SLABS.getKey(), 300);
        fuels.addTaggedFuel(Tag.WOOL.getKey(), 100);

        return fuels;
    }
}
