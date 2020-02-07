package net.glowstone.block.data;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import net.glowstone.block.data.states.StateReport;
import net.glowstone.block.data.states.StateValue;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class BlockDataConstructor {
    private final Material material;
    private final BiFunction<Material, Map<String, StateValue<?>>, ? extends BlockData> blockDataConstructor;
    private final Map<String, StateReport<?>> stateReports;
    private final Map<Integer, BlockData> blockIdsToBlockData;

    public BlockDataConstructor(
        Material material,
        BiFunction<Material, Map<String, StateValue<?>>, ? extends BlockData> blockDataConstructor,
        Map<String, StateReport<?>> stateReports,
        Map<Integer, Map<String, String>> blockIds) {
        this.material = material;
        this.blockDataConstructor = blockDataConstructor;
        this.stateReports = stateReports;
        this.blockIdsToBlockData = blockIds.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, (e) -> blockDataConstructor.apply(material, generateStateValues(e.getValue()))));
    }

    public BlockData createBlockData(Map<String, String> explicitValues) {
        return blockDataConstructor.apply(material, generateStateValues(explicitValues));
    }

    public Material getMaterial() {
        return material;
    }

    public Map<Integer, BlockData> getBlockIdsToBlockData() {
        return blockIdsToBlockData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockDataConstructor that = (BlockDataConstructor) o;
        return material == that.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(material);
    }

    private Map<String, StateValue<?>> generateStateValues(Map<String, String> explicitValues) {
        return stateReports.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                (entry) -> entry.getValue().createStateValue(Optional.ofNullable(explicitValues.get(entry.getKey())))
            ));
    }
}
