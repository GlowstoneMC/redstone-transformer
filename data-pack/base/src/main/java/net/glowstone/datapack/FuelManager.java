package net.glowstone.datapack;

import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public interface FuelManager {
    int getFuelTime(Material material);

    boolean isFuel(Material material);

    boolean addSingleFuel(Material material, int fuelTime);

    boolean addTaggedFuel(NamespacedKey tagKey, int fuelTime);

    boolean removeSingleFuel(Material material);

    boolean removeTaggedFuel(NamespacedKey tagKey);

    void clearFuels();

    void resetToDefaults();
}
