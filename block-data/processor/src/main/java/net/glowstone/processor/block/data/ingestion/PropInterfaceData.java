package net.glowstone.processor.block.data.ingestion;

import org.bukkit.block.data.BlockData;

import java.util.Set;
import javax.lang.model.type.TypeMirror;

public class PropInterfaceData {
    private final Set<PropReportMapping> propReportMappings;
    private final TypeMirror associatedInterface;
    private final Class<? extends BlockData> interfaceClass;

    public PropInterfaceData(Set<PropReportMapping> propReportMappings, TypeMirror associatedInterface, Class<? extends BlockData> interfaceClass) {
        this.propReportMappings = propReportMappings;
        this.associatedInterface = associatedInterface;
        this.interfaceClass = interfaceClass;
    }

    public Set<PropReportMapping> getPropReportMappings() {
        return propReportMappings;
    }

    public TypeMirror getAssociatedInterface() {
        return associatedInterface;
    }

    public Class<? extends BlockData> getInterfaceClass() {
        return interfaceClass;
    }
}
