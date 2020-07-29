package net.glowstone.datapack;

import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractTagManager {
    private final Map<String, Map<NamespacedKey, SubTagTrackingTag<? extends Keyed>>> tagValues;

    protected AbstractTagManager() {
        this.tagValues = this.defaultTagValues();
    }

    protected abstract Map<String, Map<NamespacedKey, SubTagTrackingTag<? extends Keyed>>> defaultTagValues();

    protected static <T extends Keyed> void addTagToMap(Map<NamespacedKey, SubTagTrackingTag<T>> tags,
                                                        String namespace,
                                                        String key,
                                                        Class<T> valueClass,
                                                        Set<T> values,
                                                        Set<SubTagTrackingTag<T>> subTags) {
        NamespacedKey namespacedKey = new NamespacedKey(namespace, key);
        tags.put(namespacedKey, new SubTagTrackingTag<T>(namespacedKey, valueClass, values, subTags));
    }

    protected static <T extends Keyed> SubTagTrackingTag<T> getTagFromMap(Map<NamespacedKey, SubTagTrackingTag<T>> tags,
                                                                          String namespace,
                                                                          String key) {
        NamespacedKey namespacedKey = new NamespacedKey(namespace, key);
        return tags.get(namespacedKey);
    }

    public SubTagTrackingTag<Material> getItemTag(String namespaceName, String key) {
        return getTag(Tag.REGISTRY_ITEMS, new NamespacedKey(namespaceName, key), Material.class);
    }

    public SubTagTrackingTag<Material> getBlockTag(String namespaceName, String key) {
        return getTag(Tag.REGISTRY_BLOCKS, new NamespacedKey(namespaceName, key), Material.class);
    }

    @SuppressWarnings("unchecked")
    public <T extends Keyed> SubTagTrackingTag<T> getTag(String registryName, NamespacedKey tagKey, Class<T> valueClass) {
        return (SubTagTrackingTag<T>) tagValues.computeIfAbsent(registryName, k -> new HashMap<>()).computeIfAbsent(tagKey, k -> new SubTagTrackingTag<T>(tagKey, valueClass, Collections.emptySet(), Collections.emptySet()));
    }
}
