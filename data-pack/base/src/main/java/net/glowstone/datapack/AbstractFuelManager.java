package net.glowstone.datapack;

import com.google.common.collect.Sets;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractFuelManager implements FuelManager {
    private final Map<Material, Integer> cachedFurnaceFuelTimes;
    private final Map<Material, Integer> explicitFurnaceFuels;
    private final Map<NamespacedKey, Integer> taggedFurnaceFuels;
    private final TagManager tagManager;

    public AbstractFuelManager(TagManager tagManager) {
        this.cachedFurnaceFuelTimes = new HashMap<>();
        this.explicitFurnaceFuels = new HashMap<>();
        this.taggedFurnaceFuels = new LinkedHashMap<>();
        this.tagManager = tagManager;

        this.loadDefaultFuels();
    }

    private void loadDefaultFuels() {
        Fuels defaults = defaultFuels();

        this.explicitFurnaceFuels.putAll(defaults.getExplicitFurnaceFuels());
        this.taggedFurnaceFuels.putAll(defaults.getTaggedFurnaceFuels());

        this.cachedFurnaceFuelTimes.putAll(this.explicitFurnaceFuels);
        this.taggedFurnaceFuels.forEach((tagKey, fuelTime) -> {
            SubTagTrackingTag<Material> tag = tagManager.getItemTag(tagKey);
            for (Material material : tag) {
                if (!this.cachedFurnaceFuelTimes.containsKey(material)) {
                    this.cachedFurnaceFuelTimes.put(material, fuelTime);
                }
            }
        });
    }

    protected abstract Fuels defaultFuels();

    @Override
    public int getFuelTime(Material material) {
        if (!cachedFurnaceFuelTimes.containsKey(material)) {
            throw new IllegalArgumentException("Material '" + material + "' is not a fuel");
        }

        return cachedFurnaceFuelTimes.get(material);
    }

    @Override
    public boolean isFuel(Material material) {
        return cachedFurnaceFuelTimes.containsKey(material);
    }

    @Override
    public boolean addSingleFuel(Material material, int fuelTime) {
        if (explicitFurnaceFuels.containsKey(material)) {
            return false;
        }

        explicitFurnaceFuels.put(material, fuelTime);
        cacheFuelTime(material);

        return true;
    }

    @Override
    public boolean addTaggedFuel(NamespacedKey tagKey, int fuelTime) {
        if (taggedFurnaceFuels.containsKey(tagKey)) {
            return false;
        }

        taggedFurnaceFuels.put(tagKey, fuelTime);

        SubTagTrackingTag<Material> tag = tagManager.getItemTag(tagKey);
        tag.forEach(this::cacheFuelTime);
        tag.addChangeListener(this::tagChangeListener);

        return true;
    }

    @Override
    public boolean removeSingleFuel(Material material) {
        if (!explicitFurnaceFuels.containsKey(material)) {
            return false;
        }

        explicitFurnaceFuels.remove(material);
        cacheFuelTime(material);

        return true;
    }

    @Override
    public boolean removeTaggedFuel(NamespacedKey tagKey) {
        if (!taggedFurnaceFuels.containsKey(tagKey)) {
            return false;
        }

        taggedFurnaceFuels.remove(tagKey);

        SubTagTrackingTag<Material> tag = tagManager.getItemTag(tagKey);
        tag.forEach(this::cacheFuelTime);
        tag.removeChangeListener(this::tagChangeListener);

        return true;
    }

    @Override
    public void clearFuels() {
        explicitFurnaceFuels.clear();

        Iterator<Entry<NamespacedKey, Integer>> taggedFurnaceFuelsIter = taggedFurnaceFuels.entrySet().iterator();

        while (taggedFurnaceFuelsIter.hasNext()) {
            Entry<NamespacedKey, Integer> entry = taggedFurnaceFuelsIter.next();
            taggedFurnaceFuelsIter.remove();
            SubTagTrackingTag<Material> tag = tagManager.getItemTag(entry.getKey());
            tag.removeChangeListener(this::tagChangeListener);
        }

        cachedFurnaceFuelTimes.clear();
    }

    @Override
    public void resetToDefaults() {
        clearFuels();
        loadDefaultFuels();
    }

    private void tagChangeListener(Set<Material> addedMaterials, Set<Material> removedMaterials) {
        Sets.union(addedMaterials, removedMaterials)
            .forEach(this::cacheFuelTime);
    }

    private void cacheFuelTime(Material material) {
        Optional<Integer> fuelTime;

        if (explicitFurnaceFuels.containsKey(material)) {
            fuelTime = Optional.of(explicitFurnaceFuels.get(material));
        } else {
            fuelTime = taggedFurnaceFuels.entrySet()
                .stream()
                .filter((entry) -> tagManager.getItemTag(entry.getKey()).isTagged(material))
                .map(Entry::getValue)
                .findFirst();
        }

        if (fuelTime.isPresent()) {
            cachedFurnaceFuelTimes.put(material, fuelTime.get());
        } else {
            cachedFurnaceFuelTimes.remove(material);
        }
    }

    private void changeValues(
        Set<Material> addedValues,
        Set<Material> removedValues,
        Set<SubTagTrackingTag<Material>> addedTags,
        Set<SubTagTrackingTag<Material>> removedTags) {

    }

    protected static final class Fuels {
        private final Map<Material, Integer> explicitFurnaceFuels;
        private final Map<NamespacedKey, Integer> taggedFurnaceFuels;

        public Fuels() {
            this.explicitFurnaceFuels = new HashMap<>();
            this.taggedFurnaceFuels = new LinkedHashMap<>();
        }

        public void addSingleFuel(Material material, int fuelTime) {
            this.explicitFurnaceFuels.put(material, fuelTime);
        }

        public void addTaggedFuel(NamespacedKey tagKey, int fuelTime) {
            this.taggedFurnaceFuels.put(tagKey, fuelTime);
        }

        public Map<Material, Integer> getExplicitFurnaceFuels() {
            return explicitFurnaceFuels;
        }

        public Map<NamespacedKey, Integer> getTaggedFurnaceFuels() {
            return taggedFurnaceFuels;
        }
    }
}
