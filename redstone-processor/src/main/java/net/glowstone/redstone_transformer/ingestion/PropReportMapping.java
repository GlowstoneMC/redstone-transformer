package net.glowstone.redstone_transformer.ingestion;

import java.util.Objects;
import net.glowstone.block.data.states.reports.StateReport;

public class PropReportMapping {
    private final String propName;
    private final Class<? extends StateReport<?>> reportType;
    private final boolean required;

    public PropReportMapping(String propName, Class<? extends StateReport<?>> reportType, boolean required) {
        this.propName = propName;
        this.reportType = reportType;
        this.required = required;
    }

    public String getPropName() {
        return propName;
    }

    public Class<? extends StateReport<?>> getReportType() {
        return reportType;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropReportMapping that = (PropReportMapping) o;
        return propName.equals(that.propName) &&
            reportType.equals(that.reportType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propName, reportType);
    }
}
