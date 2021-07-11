package net.glowstone.processor.block.data.ingestion;

import java.util.List;

public class IngestionResult {
    private final List<PropInterfaceData> propInterfaces;
    private final List<PropPolyfillData> propPolyfills;
    private final String blockDataManagerPackage;
    private final String blockDataImplPackage;
    private final String blockDataBlockManagerPackage;

    public IngestionResult(List<PropInterfaceData> propInterfaces,
                           List<PropPolyfillData> propPolyfills,
                           String blockDataManagerPackage,
                           String blockDataImplPackage,
                           String blockDataBlockManagerPackage) {
        this.propInterfaces = propInterfaces;
        this.propPolyfills = propPolyfills;
        this.blockDataManagerPackage = blockDataManagerPackage;
        this.blockDataImplPackage = blockDataImplPackage;
        this.blockDataBlockManagerPackage = blockDataBlockManagerPackage;
    }

    public List<PropInterfaceData> getPropInterfaces() {
        return propInterfaces;
    }

    public List<PropPolyfillData> getPropPolyfills() {
        return propPolyfills;
    }

    public String getBlockDataManagerPackage() {
        return blockDataManagerPackage;
    }

    public String getBlockDataImplPackage() {
        return blockDataImplPackage;
    }

    public String getBlockDataBlockManagerPackage() {
        return blockDataBlockManagerPackage;
    }
}
