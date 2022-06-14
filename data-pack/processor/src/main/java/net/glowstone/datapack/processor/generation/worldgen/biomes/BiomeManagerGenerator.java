package net.glowstone.datapack.processor.generation.worldgen.biomes;

import com.squareup.javapoet.MethodSpec;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.processor.generation.DataPackItemSourceGenerator;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;

public class BiomeManagerGenerator implements DataPackItemSourceGenerator {
    @Override
    public void addItems(String namespaceName, Data data) {
        data.getWorldGen().getBiomes().forEach((biomeName, biome) -> {

        });
    }

    @Override
    public void generateManager(Path generatedClassPath, String generatedClassNamespace) {
        MethodSpec defaultBiomesMethod = MethodSpec.methodBuilder("defaultBiomes")
                .addModifiers(Modifier.PROTECTED)
                .returns()
                .build();
    }
}
