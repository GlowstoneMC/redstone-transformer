package net.glowstone.processor.block.data.ingestion;

import org.bukkit.block.data.BlockData;

import javax.lang.model.type.TypeMirror;
import java.util.List;

public class PropPolyfillData {
    private final TypeMirror associatedInterface;
    private final List<Class<? extends BlockData>> replacesInterfaces;
    private final Class<? extends BlockData> interfaceClass;

    public PropPolyfillData(TypeMirror associatedInterface,
                            List<Class<? extends BlockData>> replacesInterfaces,
                            Class<? extends BlockData> interfaceClass) {
        this.associatedInterface = associatedInterface;
        this.replacesInterfaces = replacesInterfaces;
        this.interfaceClass = interfaceClass;
    }

    public TypeMirror getAssociatedInterface() {
        return associatedInterface;
    }

    public List<Class<? extends BlockData>> getReplacesInterfaces() {
        return replacesInterfaces;
    }

    public Class<? extends BlockData> getInterfaceClass() {
        return interfaceClass;
    }
}
