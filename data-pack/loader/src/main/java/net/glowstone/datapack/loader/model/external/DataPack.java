package net.glowstone.datapack.loader.model.external;

import java.util.Map;
import net.glowstone.datapack.loader.model.external.mcmeta.McMeta;

public class DataPack {
    private final McMeta mcMeta;
    private final Map<String, Data> namespacedData;

    public DataPack(McMeta mcMeta, Map<String, Data> namespacedData) {
        this.mcMeta = mcMeta;
        this.namespacedData = namespacedData;
    }

    public McMeta getMcMeta() {
        return mcMeta;
    }

    public Map<String, Data> getNamespacedData() {
        return namespacedData;
    }
}
