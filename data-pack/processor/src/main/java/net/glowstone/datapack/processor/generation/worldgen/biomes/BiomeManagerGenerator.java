package net.glowstone.datapack.processor.generation.worldgen.biomes;

import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.loader.model.external.worldgen.biome.BiomeDef;
import net.glowstone.datapack.processor.generation.DataPackItemSourceGenerator;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.EnumMappingArgument;
import net.glowstone.datapack.utils.mapping.IntegerMappingArgument;
import org.bukkit.block.Biome;

import javax.lang.model.element.Modifier;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class BiomeManagerGenerator implements DataPackItemSourceGenerator {

    private final CodeBlock.Builder biomeCodeBlocks = CodeBlock.builder();

    @Override
    public void addItems(String namespaceName, Data data) {
        data.getWorldGen().getBiomes().forEach((biomeName, biomeDef) -> {
            Biome biome = Biome.valueOf(biomeName.toUpperCase());

        });
    }

    @Override
    public void generateManager(Path generatedClassPath, String generatedClassNamespace) {
        MethodSpec addDefaultsMethod = MethodSpec
                .methodBuilder("addDefaults")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .build();
    }

    private List<AbstractMappingArgument> getBiomeArguments(Biome biome, int id, BiomeDef biomeDef) {
        return Collections.emptyList();
    }

    private CodeBlock createBiomeBlock(Biome biome, int id, BiomeDef biomeDef) {
        return CodeBlock.of(
                "this.addBiome($L)",
                new EnumMappingArgument(biome),
                new IntegerMappingArgument(id));
    }
}
