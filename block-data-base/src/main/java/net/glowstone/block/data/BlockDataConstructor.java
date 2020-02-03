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

    public BlockDataConstructor(
        Material material,
        BiFunction<Material, Map<String, StateValue<?>>, ? extends BlockData> blockDataConstructor,
        Map<String, StateReport<?>> stateReports) {
        this.material = material;
        this.blockDataConstructor = blockDataConstructor;
        this.stateReports = stateReports;
    }

    public BlockData createBlockData(Map<String, String> explicitValues) {
        Map<String, StateValue<?>> stateValues = stateReports.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                (entry) -> entry.getValue().createStateValue(Optional.ofNullable(explicitValues.get(entry.getKey())))
            ));
        return blockDataConstructor.apply(material, stateValues);
    }

    public Material getMaterial() {
        return material;
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
}
