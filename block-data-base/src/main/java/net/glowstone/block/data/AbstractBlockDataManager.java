package net.glowstone.block.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    public int convertToBlockId(BlockData blockData) {
        return blockDataToBlockIds.get(blockData);
    }

    public BlockData convertToBlockData(int blockId) {
        return blockIdsToBlockData.get(blockId).clone();
    }
}
