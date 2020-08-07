package net.glowstone.datapack;

import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.tags.ObservableSet;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import net.glowstone.datapack.tags.mapping.TagMapping;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractTagManager {
    private final Map<String, Map<NamespacedKey, SubTagTrackingTag<? extends Keyed>>> tagValues;

    protected AbstractTagManager() {
        this.tagValues = new HashMap<>();
        this.addDefaultTagValues();
    }

    protected abstract void addDefaultTagValues();

    public void loadFromDataPack(DataPack dataPack) {
        dataPack.getNamespacedData().forEach((namespace, data) -> {
            data.getBlockTags().forEach((itemName, tag) -> {
                TagMapping.BLOCK.provider(this, Tag.REGISTRY_BLOCKS, namespace, itemName, tag);
            });
            data.getEntityTypeTags().forEach((itemName, tag) -> {
                TagMapping.ENTITY.provider(this, "entityTypes", namespace, itemName, tag);
            });
            data.getBlockTags().forEach((itemName, tag) -> {
                TagMapping.ITEM.provider(this, Tag.REGISTRY_ITEMS, namespace, itemName, tag);
            });
        });
    }

    public SubTagTrackingTag<Material> getItemTag(String namespaceName, String key) {
        return getItemTag(new NamespacedKey(namespaceName, key));
    }

    public SubTagTrackingTag<Material> getItemTag(NamespacedKey namespacedKey) {
        return getTag(Tag.REGISTRY_ITEMS, namespacedKey, Material.class);
    }

    public SubTagTrackingTag<Material> getBlockTag(String namespaceName, String key) {
        return getBlockTag(new NamespacedKey(namespaceName, key));
    }

    public SubTagTrackingTag<Material> getBlockTag(NamespacedKey namespacedKey) {
        return getTag(Tag.REGISTRY_BLOCKS, namespacedKey, Material.class);
    }

    public SubTagTrackingTag<EntityType> getEntityTag(String namespaceName, String key) {
        return getEntityTag(new NamespacedKey(namespaceName, key));
    }

    public SubTagTrackingTag<EntityType> getEntityTag(NamespacedKey namespacedKey) {
        return getTag("entityTypes", namespacedKey, EntityType.class);
    }
    public <T extends Keyed> SubTagTrackingTag<T> getTag(String registryName, String namespace, String key, Class<T> valueClass) {
        return getTag(registryName, new NamespacedKey(namespace, key), valueClass);
    }

    @SuppressWarnings("unchecked")
    public <T extends Keyed> SubTagTrackingTag<T> getTag(String registryName, NamespacedKey tagKey, Class<T> valueClass) {
        return (SubTagTrackingTag<T>) tagValues.computeIfAbsent(registryName, k -> new HashMap<>()).computeIfAbsent(tagKey, k -> new SubTagTrackingTag<T>(tagKey, valueClass, Collections.emptySet(), Collections.emptySet()));
    }

    public <T extends Keyed> SubTagTrackingTag<T> addTag(String registry, String namespace, String key, Class<T> valueClass, Set<T> directValues, Set<SubTagTrackingTag<? extends T>> subSets) {
        return addTag(registry, new NamespacedKey(namespace, key), valueClass, directValues, subSets);
    }

    public <T extends Keyed> SubTagTrackingTag<T> addTag(String registry, NamespacedKey key, Class<T> valueClass, Set<T> directValues, Set<SubTagTrackingTag<? extends T>> subSets) {
        SubTagTrackingTag<T> tag = getTag(registry, key, valueClass);
        tag.replaceValues(Optional.of(directValues), Optional.of(subSets));
        return tag;
    }
}
