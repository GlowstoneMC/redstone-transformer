package net.glowstone.datapack;

import net.glowstone.datapack.tags.ExpandableTag;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractTagManager {
    private final Map<String, Map<NamespacedKey, Set<? extends Keyed>>> tagValues;

    protected AbstractTagManager() {
        this.tagValues = this.defaultTagValues();
    }

    protected abstract Map<String, Map<NamespacedKey, Set<? extends Keyed>>> defaultTagValues();

    protected static <T extends Keyed> Tag<T> createSubTag(Map<NamespacedKey, Set<? extends Keyed>> tags, String namespace, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(namespace, key);
        return new ExpandableTag<>(namespacedKey, tags.get(namespacedKey));
    }

    public Tag<Material> getItemTag(String namespaceName, String key) {
        return getTag(Tag.REGISTRY_ITEMS, new NamespacedKey(namespaceName, key));
    }

    public Tag<Material> getBlockTag(String namespaceName, String key) {
        return getTag(Tag.REGISTRY_BLOCKS, new NamespacedKey(namespaceName, key));
    }

    public <T extends Keyed> Tag<T> getTag(String registryName, NamespacedKey tagKey) {
        return new ExpandableTag<>(tagKey, tagValues.computeIfAbsent(registryName, k -> new HashMap<>()).computeIfAbsent(tagKey, k -> new HashSet<T>()));
    }
}
