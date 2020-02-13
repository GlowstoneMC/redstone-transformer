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
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public abstract class AbstractBlockDataManager  {
    private final Map<Material, BlockDataConstructor> blockDataConstructorsByMaterial;
    private final Map<Integer, BlockData> blockIdsToBlockData;
    private final Map<BlockData, Integer> blockDataToBlockIds;

    public AbstractBlockDataManager(Set<BlockDataConstructor> blockDataConstructors) {
        this.blockDataConstructorsByMaterial = blockDataConstructors.stream()
            .collect(Collectors.toMap(BlockDataConstructor::getMaterial, Function.identity()));

        this.blockIdsToBlockData = blockDataConstructors.stream()
            .flatMap((bdc) -> bdc.getBlockIdsToBlockData().entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.blockDataToBlockIds = this.blockIdsToBlockData.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    public BlockData createBlockData(Material material) {
        BlockDataConstructor blockDataConstructor = blockDataConstructorsByMaterial.get(material);
        return blockDataConstructor.createBlockData(Collections.emptyMap());
    }

    public BlockData createBlockData(Material material, Consumer<BlockData> consumer) {
        BlockData blockData = this.createBlockData(material);
        consumer.accept(blockData);
        return blockData;
    }

    public BlockData createBlockData(String data) throws IllegalArgumentException {
        if (data.endsWith("]")) {
            int stateStart = data.indexOf("[");
            String materialName = data.substring(0, stateStart);
            return createBlockData(Material.matchMaterial(materialName), data.substring(stateStart + 1, data.length() - 1));
        }
        throw new IllegalArgumentException("Illegal format for data: " + data);
    }

    public BlockData createBlockData(Material material, String data) {
        Map<String, String> stateMAp = Arrays.stream(data.split(","))
            .map((s) -> s.trim().split("=", 2))
            .collect(Collectors.toMap((split) -> split[0], (split) -> split[1]));
        return blockDataConstructorsByMaterial.get(material).createBlockData(stateMAp);
    }

    public int convertToBlockId(BlockData blockData) {
        return blockDataToBlockIds.get(blockData);
    }

    public BlockData convertToBlockData(int blockId) {
        return blockIdsToBlockData.get(blockId).clone();
    }
}
