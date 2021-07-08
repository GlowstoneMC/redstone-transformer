package net.glowstone.datapack;

import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

import java.util.Optional;
import java.util.Set;

public interface TagManager {
    void loadFromDataPack(DataPack dataPack);

    void clear();

    void resetToDefaults();

    Set<String> getAllTagRegistries();

    Set<NamespacedKey> getAllKeysInRegistry(String registryName);

    SubTagTrackingTag<Material> getItemTag(String namespaceName, String key);

    SubTagTrackingTag<Material> getItemTag(NamespacedKey namespacedKey);

    SubTagTrackingTag<Material> getBlockTag(String namespaceName, String key);

    SubTagTrackingTag<Material> getBlockTag(NamespacedKey namespacedKey);

    SubTagTrackingTag<EntityType> getEntityTag(String namespaceName, String key);

    SubTagTrackingTag<EntityType> getEntityTag(NamespacedKey namespacedKey);

    <T extends Keyed> SubTagTrackingTag<T> getTag(String registryName, String namespace, String key, Class<T> valueClass);

    @SuppressWarnings("unchecked")
    <T extends Keyed> SubTagTrackingTag<T> getTag(String registryName, NamespacedKey tagKey, Class<T> valueClass);

    Optional<SubTagTrackingTag<? extends Keyed>> getTag(String registryName, NamespacedKey tagKey);
}
