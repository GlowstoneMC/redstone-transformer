package net.glowstone.redstone_transformer.ingestion;

import javax.lang.model.type.TypeMirror;
import net.glowstone.block.data.states.reports.StateReport;

public class PropInterfaceData {
    private final String propName;
    private final TypeMirror propInterface;
    private final Class<? extends StateReport<?>> reportType;
    private final String interfaceName;

    public PropInterfaceData(String propName, TypeMirror propInterface, Class<? extends StateReport<?>> reportType, String interfaceName) {
        this.propName = propName;
        this.propInterface = propInterface;
        this.reportType = reportType;
        this.interfaceName = interfaceName;
    }

    public String getPropName() {
        return propName;
    }

    public TypeMirror getPropInterface() {
        return propInterface;
    }

    public Class<? extends StateReport<?>> getReportType() {
        return reportType;
    }

    public String getInterfaceName() {
        return interfaceName;
    }
}
