package net.glowstone.processor.block.data.ingestion;

import java.util.List;

public class IngestionResult {
    private final List<PropInterfaceData> propInterfaces;
    private final String blockDataManagerPackage;
    private final String blockDataImplPackage;

    public IngestionResult(List<PropInterfaceData> propInterfaces, String blockDataManagerPackage, String blockDataImplPackage) {
        this.propInterfaces = propInterfaces;
        this.blockDataManagerPackage = blockDataManagerPackage;
        this.blockDataImplPackage = blockDataImplPackage;
    }

    public List<PropInterfaceData> getPropInterfaces() {
        return propInterfaces;
    }

    public String getBlockDataManagerPackage() {
        return blockDataManagerPackage;
    }

    public String getBlockDataImplPackage() {
        return blockDataImplPackage;
    }
}
