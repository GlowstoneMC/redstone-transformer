package net.glowstone.block.data;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public abstract class AbstractBlockDataManager {
    @SafeVarargs
    protected static <K, V> Map<K, V> createMap(Map.Entry<K, V>... entries) {
        return Collections.unmodifiableMap(Arrays.stream(entries).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    protected static <K, V> Map.Entry<K, V> createEntry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }

    protected static <T> Set<T> createSet(T... values) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(values)));
    }

    private final Map<Material, BlockDataConstructor> blockDataConstructorsByMaterial;

    public AbstractBlockDataManager(Set<BlockDataConstructor> blockDataConstructors) {
        this.blockDataConstructorsByMaterial = blockDataConstructors.stream()
            .collect(Collectors.toMap(BlockDataConstructor::getMaterial, Function.identity()));
    }

    public BlockData createBlockData(Material material) {
        BlockDataConstructor blockDataConstructor = blockDataConstructorsByMaterial.get(material);
        return blockDataConstructor.createBlockData(Collections.emptyMap());
    }

}
