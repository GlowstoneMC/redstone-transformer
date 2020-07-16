package net.glowstone.datapack.processor.generation.tags;

import com.google.common.collect.Sets;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.Traverser;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.loader.model.external.tag.Tag;
import net.glowstone.datapack.processor.generation.DataPackItemSourceGenerator;
import net.glowstone.datapack.processor.generation.utils.NamespaceUtils;
import net.glowstone.datapack.tags.ExpandableTag;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagManagerGenerator implements DataPackItemSourceGenerator {
    private static final ParameterizedTypeName NAMESPACED_KEYED_SET_MAP = ParameterizedTypeName.get(
        ClassName.get(Map.class),
        ClassName.get(NamespacedKey.class),
        ParameterizedTypeName.get(
            ClassName.get(Set.class),
            WildcardTypeName.subtypeOf(Keyed.class)
        )
    );

    private static CodeBlock.Builder createTagCodeBlock() {
        return CodeBlock.builder()
            .addStatement(
                "$T tags = new $T<>()",
                NAMESPACED_KEYED_SET_MAP,
                HashMap.class
            );
    }

    private final CodeBlock.Builder blockTagCodeBlocks = createTagCodeBlock();
    private final CodeBlock.Builder itemTagCodeBlocks = createTagCodeBlock();
    private final CodeBlock.Builder entityTagCodeBlocks = createTagCodeBlock();
    private final CodeBlock.Builder fluidTagCodeBlocks = createTagCodeBlock();

    @Override
    public void addItems(String namespaceName, Data data) {
        traverseTags(namespaceName, data.getBlockTags())
            .forEach(new TagAccumulator<>(namespaceName, blockTagCodeBlocks, Material.class, Material::matchMaterial));
        traverseTags(namespaceName, data.getItemTags())
            .forEach(new TagAccumulator<>(namespaceName, itemTagCodeBlocks, Material.class, Material::matchMaterial));
        traverseTags(namespaceName, data.getEntityTypeTags())
            .forEach(new TagAccumulator<>(namespaceName, entityTagCodeBlocks, EntityType.class, (s) -> EntityType.fromName(NamespaceUtils.parseNamespace(s, namespaceName).getKey())));
    }

    @Override
    public void generateManager(Path generatedClassPath, String generatedClassNamespace) {
        MethodSpec defaultBlockTags = createDefaultTagMethod("defaultBlockTags", blockTagCodeBlocks);
        MethodSpec defaultItemTags = createDefaultTagMethod("defaultItemTags", itemTagCodeBlocks);
        MethodSpec defaultEntityTags = createDefaultTagMethod("defaultEntityTags", entityTagCodeBlocks);
        MethodSpec defaultFluidTags = createDefaultTagMethod("defaultFluidTags", fluidTagCodeBlocks);

        ParameterizedTypeName returnType = ParameterizedTypeName.get(
            ClassName.get(Map.class),
            ClassName.get(String.class),
            NAMESPACED_KEYED_SET_MAP
        );
        MethodSpec defaultTags = MethodSpec
            .methodBuilder("defaultTagValues")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PROTECTED)
            .returns(returnType)
            .addStatement(
                "$T tags = new $T<>()",
                returnType,
                HashMap.class
            )
            .addStatement(
                "tags.put($S, $N())",
                org.bukkit.Tag.REGISTRY_BLOCKS,
                defaultBlockTags
            )
            .addStatement(
                "tags.put($S, $N())",
                org.bukkit.Tag.REGISTRY_ITEMS,
                defaultItemTags
            )
            .addStatement(
                "tags.put($S, $N())",
                "entityTypes",
                defaultEntityTags
            )
            .addStatement(
                "tags.put($S, $N())",
                "fluids",
                defaultFluidTags
            )
            .addStatement("return tags")
            .build();

        TypeSpec tagManagerTypeSpec = TypeSpec.classBuilder("TagManager")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(AbstractTagManager.class)
            .addMethod(defaultBlockTags)
            .addMethod(defaultItemTags)
            .addMethod(defaultEntityTags)
            .addMethod(defaultFluidTags)
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
    
    private MethodSpec createDefaultTagMethod(String methodName, CodeBlock.Builder tagCodeBlock) {
        tagCodeBlock.addStatement("return tags");
        return MethodSpec
            .methodBuilder(methodName)
            .addModifiers(Modifier.PRIVATE)
            .returns(NAMESPACED_KEYED_SET_MAP)
            .addCode(tagCodeBlock.build())
            .build();
    }

    private static class TagAccumulator<T extends Enum<T>> implements Consumer<Entry<String, Tag>> {
        private final String namespaceName;
        private final CodeBlock.Builder tagBlock;
        private final Class<T> valueType;
        private final Function<String, T> enumConverter;

        private TagAccumulator(String namespaceName, CodeBlock.Builder tagBlock, Class<T> valueType, Function<String, T> enumConverter) {
            this.namespaceName = namespaceName;
            this.tagBlock = tagBlock;
            this.valueType = valueType;
            this.enumConverter = enumConverter;
        }

        @Override
        public void accept(Entry<String, Tag> entry) {
            String itemName = entry.getKey();
            Tag tag = entry.getValue();
            CodeBlock.Builder tagsAddBlock = CodeBlock.builder()
                .add(
                    "tags.put(new $T($S, $S), $T.newHashSet(",
                    NamespacedKey.class,
                    namespaceName,
                    itemName,
                    Sets.class
                );
            for (int i = 0; i < tag.getValues().size(); i++) {
                if (i != 0) {
                    tagsAddBlock.add(", ");
                }
                String tagValue = tag.getValues().get(i);
                if (tagValue.startsWith("#")) {
                    NamespacedKey namespacedKey = NamespaceUtils.parseNamespace(tagValue.substring(1), namespaceName);
                    tagsAddBlock.add(
                        "this.<$T>createSubTag(tags, $S, $S)",
                        valueType,
                        namespacedKey.getNamespace(),
                        namespacedKey.getKey()
                    );
                } else {
                    tagsAddBlock.add(
                        "$T.$L",
                        valueType,
                        enumConverter.apply(tagValue)
                    );
                }
            }
            tagsAddBlock.add("))");
            tagBlock.addStatement(tagsAddBlock.build());
        }
    }
}
