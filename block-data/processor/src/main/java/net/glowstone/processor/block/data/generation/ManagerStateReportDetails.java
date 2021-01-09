package net.glowstone.processor.block.data.generation;

import java.util.Objects;
import java.util.Set;
import net.glowstone.block.data.states.reports.StateReport;

public class ManagerStateReportDetails {
    private final String propName;
    private final String defaultValue;
    private final Set<String> validValues;
    private final Class<? extends StateReport<?>> stateReportType;

    public ManagerStateReportDetails(String propName, String defaultValue, Set<String> validValues, Class<? extends StateReport<?>> stateReportType) {
        this.propName = propName;
        this.defaultValue = defaultValue;
        this.validValues = validValues;
        this.stateReportType = stateReportType;
    }

    public String getPropName() {
        return propName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Set<String> getValidValues() {
        return validValues;
    }

    public Class<? extends StateReport<?>> getStateReportType() {
        return stateReportType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerStateReportDetails that = (ManagerStateReportDetails) o;
        return propName.equals(that.propName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propName);
    }
}
