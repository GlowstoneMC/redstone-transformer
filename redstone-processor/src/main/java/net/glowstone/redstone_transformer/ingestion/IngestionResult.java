package net.glowstone.redstone_transformer.ingestion;

import java.util.Map;

public class IngestionResult {
    private final Map<String, PropInterfaceData> propInterfaces;
    private final String blockDataManagerPackage;
    private final String blockDataImplPackage;

    public IngestionResult(Map<String, PropInterfaceData> propInterfaces, String blockDataManagerPackage, String blockDataImplPackage) {
        this.propInterfaces = propInterfaces;
        this.blockDataManagerPackage = blockDataManagerPackage;
        this.blockDataImplPackage = blockDataImplPackage;
    }

    public Map<String, PropInterfaceData> getPropInterfaces() {
        return propInterfaces;
    }

    public String getBlockDataManagerPackage() {
        return blockDataManagerPackage;
    }

    public String getBlockDataImplPackage() {
        return blockDataImplPackage;
    }
}
