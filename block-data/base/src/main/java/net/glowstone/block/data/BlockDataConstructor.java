package net.glowstone.block.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Streams;
import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.StateReport;
import net.glowstone.block.data.states.values.StateValue;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class BlockDataConstructor {
    private final Material material;
    private final ConstructorFunction blockDataConstructor;
    private final Map<String, StateReport<?>> stateReports;
    private final Map<Integer, StatefulBlockData> blockIdsToBlockData;
    private final Map<Map<String, String>, StatefulBlockData> blockPropsToBlockData;

    private BlockDataConstructor(
        Material material,
        ConstructorFunction blockDataConstructor,
        Map<String, StateReport<?>> stateReports,
        Map<Integer, Map<String, String>> blockIds) {
        this.material = material;
        this.blockDataConstructor = blockDataConstructor;
        this.stateReports = stateReports;

        ImmutableMap.Builder<Integer, StatefulBlockData> blockIdsToBlockData = ImmutableMap.builder();
        ImmutableMap.Builder<Map<String, String>, StatefulBlockData> blockPropsToBlockData = ImmutableMap.builder();
        Map<String, String> defaultProps = blockDataConstructor.construct(material, generateStateValues(Collections.emptyMap()), false).getSerializedStateProps();

        blockIds.forEach((key, value) -> {
            StatefulBlockData instance = blockDataConstructor.construct(material, generateStateValues(value), false);
            blockIdsToBlockData.put(key, instance);
            blockPropsToBlockData.putAll(generateDefaultPermutations(defaultProps, value, instance));
        });

        this.blockIdsToBlockData = blockIdsToBlockData.build();
        this.blockPropsToBlockData = blockPropsToBlockData.build();
    }

    public BlockData createBlockData(Map<String, String> explicitValues, boolean explicit) {
        return blockDataConstructor.construct(material, generateStateValues(explicitValues), explicit);
    }

    public Material getMaterial() {
        return material;
    }

    public Map<Integer, StatefulBlockData> getBlockIdsToBlockData() {
        return blockIdsToBlockData;
    }

    public Map<Map<String, String>, StatefulBlockData> getBlockPropsToBlockData() {
        return blockPropsToBlockData;
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
            .collect(ImmutableMap.toImmutableMap(
                Entry::getKey,
                (entry) -> entry.getValue().createStateValue(Optional.ofNullable(explicitValues.get(entry.getKey())))
            ));
    }

    private Map<Map<String, String>, StatefulBlockData> generateDefaultPermutations(Map<String, String> defaultProps, Map<String, String> props, StatefulBlockData instance) {
        Set<Entry<String, String>> defaultValues = new HashSet<>(props.entrySet());
        Set<Entry<String, String>> nonDefaultValues = new HashSet<>(props.entrySet());

        defaultValues.retainAll(defaultProps.entrySet());
        nonDefaultValues.removeAll(defaultProps.entrySet());

        Collection<List<Entry<String, String>>> permutations = new ArrayList<>(Collections2.permutations(defaultValues));

        // If we are the default, then also add the unspecified, empty case
        if (nonDefaultValues.isEmpty()) {
            permutations.add(Collections.emptyList());
        }

        return permutations.stream()
                           .map((permutatedProps) -> {
                               return Streams.concat(permutatedProps.stream(), nonDefaultValues.stream())
                                             .collect(ImmutableMap.toImmutableMap(Entry::getKey, Entry::getValue));
                           })
                           .distinct()
                           .collect(ImmutableMap.toImmutableMap(Function.identity(), (e) -> instance));
    }

    public static Builder builder(Material material, ConstructorFunction blockDataConstructor) {
        return new Builder(material, blockDataConstructor);
    }

    public static class Builder {
        private final Material material;
        private final ConstructorFunction blockDataConstructor;
        private final Map<String, StateReport<?>> stateReports;
        private final Map<Integer, BlockIdPropsMapper> blockIdPropsMappers;

        private Builder(Material material, ConstructorFunction blockDataConstructor) {
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
                .collect(ImmutableMap.toImmutableMap(Entry::getKey, (e) -> ImmutableMap.copyOf(e.getValue().props)));
            return new BlockDataConstructor(material, blockDataConstructor, ImmutableMap.copyOf(stateReports), blockIds);
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

    @FunctionalInterface
    public interface ConstructorFunction {
        StatefulBlockData construct(Material material, Map<String, StateValue<?>> stateValues, boolean explicit);
    }
}
