package net.glowstone.block.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import net.glowstone.block.data.states.reports.StateReport;
import net.glowstone.block.data.states.values.StateValue;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class BlockDataConstructor {
    private final Material material;
    private final BiFunction<Material, Map<String, StateValue<?>>, ? extends BlockData> blockDataConstructor;
    private final Map<String, StateReport<?>> stateReports;
    private final Map<Integer, BlockData> blockIdsToBlockData;

    private BlockDataConstructor(
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

    public static Builder builder(Material material, BiFunction<Material, Map<String, StateValue<?>>, ? extends BlockData> blockDataConstructor) {
        return new Builder(material, blockDataConstructor);
    }

    public static class Builder {
        private final Material material;
        private final BiFunction<Material, Map<String, StateValue<?>>, ? extends BlockData> blockDataConstructor;
        private final Map<String, StateReport<?>> stateReports;
        private final Map<Integer, BlockIdPropsMapper> blockIdPropsMappers;

        private Builder(Material material, BiFunction<Material, Map<String, StateValue<?>>, ? extends BlockData> blockDataConstructor) {
            this.material = material;
            this.blockDataConstructor = blockDataConstructor;
            this.stateReports = new HashMap<>();
            this.blockIdPropsMappers = new HashMap<>();
        }

        public Builder associatePropWithReport(String prop, StateReport<?> report) {
            stateReports.put(prop, report);
            return this;
        }

        public BlockIdPropsMapper associateId(int id) {
            return blockIdPropsMappers.compute(id, (ignored1, ignored2) -> new BlockIdPropsMapper(this));
        }

        public BlockDataConstructor build() {
            Map<Integer, Map<String, String>> blockIds = blockIdPropsMappers.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, (e) -> Collections.unmodifiableMap(e.getValue().props)));
            return new BlockDataConstructor(material, blockDataConstructor, Collections.unmodifiableMap(stateReports), Collections.unmodifiableMap(blockIds));
        }
    }

    public static class BlockIdPropsMapper {
        private final Builder builder;
        private final Map<String, String> props;

        public BlockIdPropsMapper(Builder builder) {
            this.builder = builder;
            this.props = new HashMap<>();
        }

        public BlockIdPropsMapper withProp(String key, String value) {
            props.put(key, value);
            return this;
        }

        public BlockIdPropsMapper associateId(int id) {
            return builder.associateId(id);
        }

        public Builder associatePropWithReport(String prop, StateReport<?> report) {
            return builder.associatePropWithReport(prop, report);
        }

        public BlockDataConstructor build() {
            return builder.build();
        }
    }
}
