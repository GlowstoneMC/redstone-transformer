package net.glowstone.datapack.processor.generation.tags;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.loader.model.external.tag.Tag;
import net.glowstone.datapack.processor.generation.DataPackItemSourceGenerator;
import net.glowstone.datapack.processor.generation.MappingArgumentGeneratorRegistry;
import net.glowstone.datapack.tags.mapping.TagMapping;
import net.glowstone.datapack.utils.NamespaceUtils;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class TagManagerGenerator implements DataPackItemSourceGenerator {
    private final CodeBlock.Builder tagCodeBlocks = CodeBlock.builder();

    @Override
    public void addItems(String namespaceName, Data data) {
        traverseTags(namespaceName, data.getBlockTags())
            .forEach((entry) -> tagCodeBlocks.addStatement(createTagBlock(org.bukkit.Tag.REGISTRY_BLOCKS, namespaceName, entry, TagMapping.BLOCK)));
        traverseTags(namespaceName, data.getItemTags())
            .forEach((entry) -> tagCodeBlocks.addStatement(createTagBlock(org.bukkit.Tag.REGISTRY_ITEMS, namespaceName, entry, TagMapping.ITEM)));
        traverseTags(namespaceName, data.getEntityTypeTags())
            .forEach((entry) -> tagCodeBlocks.addStatement(createTagBlock("entityTypes", namespaceName, entry, TagMapping.ENTITY)));
    }

    @Override
    public void generateManager(Path generatedClassPath, String generatedClassNamespace) {
        MethodSpec defaultTags = MethodSpec
            .methodBuilder("addDefaultTagValues")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PROTECTED)
            .addCode(tagCodeBlocks.build())
            .build();

        TypeSpec tagManagerTypeSpec = TypeSpec.classBuilder("VanillaTagManager")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(AbstractTagManager.class)
            .addMethod(defaultTags)
            .build();

        JavaFile tagManagerJavaFile = JavaFile.builder(generatedClassNamespace, tagManagerTypeSpec)
            .indent("    ")
            .build();

        try {
            tagManagerJavaFile.writeTo(generatedClassPath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private int tagDepth(Map<String, Tag> tags, Map<NamespacedKey, Integer> depthCounts, NamespacedKey key) {
        if (depthCounts.containsKey(key)) {
            return depthCounts.get(key);
        }

        int maxDepth = tags.get(key.getKey()).getValues()
            .stream()
            .mapToInt((value) -> {
                if (value.startsWith("#")) {
                    return tagDepth(tags, depthCounts, NamespaceUtils.parseNamespace(value.substring(1), key.getNamespace())) + 1;
                } else {
                    return 0;
                }
            })
            .max()
            .getAsInt();

        depthCounts.put(key, maxDepth + 1);
        return maxDepth + 1;
    }

    private Stream<Entry<String, Tag>> traverseTags(String namespaceName, Map<String, Tag> tags) {
        Map<NamespacedKey, Integer> depthCounts = new HashMap<>();

        tags.forEach((itemName, tag) -> {
            tagDepth(tags, depthCounts, new NamespacedKey(namespaceName, itemName));
        });

        return tags.entrySet()
            .stream()
            .sorted(Comparator.comparingInt(o -> depthCounts.get(new NamespacedKey(namespaceName, o.getKey()))));
    }

    private CodeBlock createTagBlock(String registry, String namespace, Entry<String, Tag> entry, TagMapping<?> tagMapping) {
        return CodeBlock.of(
            "this.addTag($L)",
            tagMapping.providerArguments(registry, namespace, entry.getKey(), entry.getValue())
                .stream()
                .map((v) -> MappingArgumentGeneratorRegistry.mapArgument("this", v))
                .collect(CodeBlock.joining(", "))
        );
    }
}
