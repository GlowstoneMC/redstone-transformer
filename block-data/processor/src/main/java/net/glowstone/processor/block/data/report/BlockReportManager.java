package net.glowstone.processor.block.data.report;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class BlockReportManager {
    private final BiMap<Integer, InternalBlockState> blockIdToBlockState;
    private final Map<String, InternalBlockProps> blockNameToProps;

    public BlockReportManager(final Map<String, BlockReportModel> blockReports) {
        final ImmutableBiMap.Builder<Integer, InternalBlockState> blockIdToBlockState =
            ImmutableBiMap.builder();
        final ImmutableMap.Builder<String, InternalBlockProps> blockNameToProps =
            ImmutableMap.builder();

        blockReports.forEach((blockName, blockReport) -> {
            Optional<Integer> defaultState = Optional.empty();
            for (BlockStateModel blockState : blockReport.getStates()) {
                final InternalBlockState internalState = new InternalBlockState(
                    blockName,
                    ImmutableMap.copyOf(blockState.getProperties())
                );
                blockIdToBlockState.put(blockState.getId(), internalState);

                if (blockState.isDefaultState()) {
                    defaultState = Optional.of(blockState.getId());
                }
            }

            final Map<String, Set<String>> validProps = ImmutableMap.copyOf(blockReport.getProperties());
            final InternalBlockProps internalProps = new InternalBlockProps(
                validProps,
                defaultState.get()
            );
            blockNameToProps.put(blockName, internalProps);
        });

        this.blockIdToBlockState = blockIdToBlockState.build();
        this.blockNameToProps = blockNameToProps.build();
    }

    public BiMap<Integer, InternalBlockState> getBlockIdToBlockState() {
        return blockIdToBlockState;
    }

    public Map<String, InternalBlockProps> getBlockNameToProps() {
        return blockNameToProps;
    }

    public static class InternalBlockState {
        private final String blockName;
        private final Map<String, String> properties;

        public InternalBlockState(String blockName, Map<String, String> properties) {
            this.blockName = blockName;
            this.properties = properties;
        }

        public String getBlockName() {
            return blockName;
        }

        public Map<String, String> getProperties() {
            return properties;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InternalBlockState that = (InternalBlockState) o;
            return Objects.equals(blockName, that.blockName) &&
                Objects.equals(properties, that.properties);
        }

        @Override
        public int hashCode() {
            return Objects.hash(blockName, properties);
        }
    }

    public static class InternalBlockProps {
        private final Map<String, Set<String>> validProps;
        private final int defaultState;

        public InternalBlockProps(Map<String, Set<String>> validProps, int defaultState) {
            this.validProps = validProps;
            this.defaultState = defaultState;
        }

        public Map<String, Set<String>> getValidProps() {
            return validProps;
        }

        public int getDefaultState() {
            return defaultState;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InternalBlockProps that = (InternalBlockProps) o;
            return defaultState == that.defaultState &&
                Objects.equals(validProps, that.validProps);
        }

        @Override
        public int hashCode() {
            return Objects.hash(validProps, defaultState);
        }
    }
}
