package net.glowstone.redstone_transformer.generation;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.bukkit.Material;

public class ManagerBlockDataDetails {
    private final Material material;
    private final String blockDataSimpleName;
    private final Map<Integer, Map<String, String>> blockIds;
    private final Set<ManagerStateReportDetails> stateReportDetails;

    public ManagerBlockDataDetails(Material material, String blockDataSimpleName, Map<Integer, Map<String, String>> blockIds, Set<ManagerStateReportDetails> stateReportDetails) {
        this.material = material;
        this.blockDataSimpleName = blockDataSimpleName;
        this.blockIds = blockIds;
        this.stateReportDetails = stateReportDetails;
    }

    public Material getMaterial() {
        return material;
    }

    public String getBlockDataSimpleName() {
        return blockDataSimpleName;
    }

    public Map<Integer, Map<String, String>> getBlockIds() {
        return blockIds;
    }

    public Set<ManagerStateReportDetails> getStateReportDetails() {
        return stateReportDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerBlockDataDetails that = (ManagerBlockDataDetails) o;
        return Objects.equals(material, that.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material);
    }
}
