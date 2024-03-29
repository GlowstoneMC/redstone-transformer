package net.glowstone.datapack.processor.generation;

import net.glowstone.datapack.loader.model.external.Data;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public interface DataPackItemSourceGenerator {
    void addItems(String namespaceName,
                  Data data);

    void generateManager(Path generatedClassPath,
                         String generatedClassNamespace);
}
