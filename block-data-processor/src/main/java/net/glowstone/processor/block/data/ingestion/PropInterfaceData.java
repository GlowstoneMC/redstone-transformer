package net.glowstone.processor.block.data.ingestion;

import java.util.Set;
import javax.lang.model.type.TypeMirror;

public class PropInterfaceData {
    private final Set<PropReportMapping> propReportMappings;
    private final TypeMirror associatedInterface;
    private final String interfaceName;

    public PropInterfaceData(Set<PropReportMapping> propReportMappings, TypeMirror associatedInterface, String interfaceName) {
        this.propReportMappings = propReportMappings;
        this.associatedInterface = associatedInterface;
        this.interfaceName = interfaceName;
    }

    public Set<PropReportMapping> getPropReportMappings() {
        return propReportMappings;
    }

    public TypeMirror getAssociatedInterface() {
        return associatedInterface;
    }

    public String getInterfaceName() {
        return interfaceName;
    }
}
