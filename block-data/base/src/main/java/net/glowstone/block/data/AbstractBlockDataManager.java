package net.glowstone.block.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import net.glowstone.block.data.states.StatefulBlockData;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public abstract class AbstractBlockDataManager  {
    private final BiMap<Integer, StatefulBlockData> blockIdsToBlockData;
    private final Map<Material, Map<Map<String, String>, StatefulBlockData>> blockPropsToBlockData;

    public AbstractBlockDataManager(Set<BlockDataConstructor> blockDataConstructors) {
        ImmutableBiMap.Builder<Integer, StatefulBlockData> blockIdsToBlockData = ImmutableBiMap.builder();
        ImmutableMap.Builder<Material, Map<Map<String, String>, StatefulBlockData>> blockPropsToBlockData = ImmutableMap.builder();

        blockDataConstructors.forEach((bdc) -> {
            blockIdsToBlockData.putAll(bdc.getBlockIdsToBlockData());
            blockPropsToBlockData.put(bdc.getMaterial(), bdc.getBlockPropsToBlockData());
        });

        this.blockIdsToBlockData = blockIdsToBlockData.build();
        this.blockPropsToBlockData = blockPropsToBlockData.build();
    }

    public StatefulBlockData createBlockData(Material material) {
        return createBlockData(material, Collections.emptyMap());
    }

    public StatefulBlockData createBlockData(Material material, Consumer<BlockData> consumer) {
        StatefulBlockData blockData = this.createBlockData(material);
        consumer.accept(blockData);
        return blockData;
    }

    public StatefulBlockData createBlockData(String data) throws IllegalArgumentException {
        if (data.endsWith("]")) {
            int stateStart = data.indexOf("[");
            String materialName = data.substring(0, stateStart);
            return createBlockData(Material.matchMaterial(materialName), data.substring(stateStart + 1, data.length() - 1));
        }
        throw new IllegalArgumentException("Illegal format for data: " + data);
    }

    public StatefulBlockData createBlockData(Material material, String data) {
        Map<String, String> stateMap = Arrays.stream(data.split(","))
            .map((s) -> s.trim().split("=", 2))
            .collect(Collectors.toMap((split) -> split[0], (split) -> split[1]));
        return createBlockData(material, stateMap);
    }

    private StatefulBlockData createBlockData(Material material, Map<String, String> props) {
        return blockPropsToBlockData.get(material).get(props).clone();
    }

    public int convertToBlockId(StatefulBlockData blockData) {
        return blockIdsToBlockData.inverse().get(blockData);
    }

    public StatefulBlockData convertToStatefulBlockData(BlockData blockData) {
        if (StatefulBlockData.class.isAssignableFrom(blockData.getClass())) {
            return (StatefulBlockData) blockData;
        }

        return createBlockData(blockData.getAsString(true));
    }

    public BlockData convertToBlockData(int blockId) {
        return blockIdsToBlockData.get(blockId).clone();
    }
}
