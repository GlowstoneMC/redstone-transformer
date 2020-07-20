package net.glowstone.datapack.processor.generation.tags;

import com.google.common.collect.Sets;
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
import net.glowstone.datapack.processor.generation.utils.NamespaceUtils;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class TagManagerGenerator implements DataPackItemSourceGenerator {
    private static final ParameterizedTypeName NAMESPACED_KEYED_SET_MAP = createNamespacedSetMap(WildcardTypeName.subtypeOf(Keyed.class));

    private static ParameterizedTypeName createNamespacedSetMap(TypeName valueType) {
        return ParameterizedTypeName.get(
            ClassName.get(Map.class),
            ClassName.get(NamespacedKey.class),
            ParameterizedTypeName.get(
                ClassName.get(SubTagTrackingTag.class),
                valueType
            )
        );
    }

    private static CodeBlock.Builder createTagCodeBlock(Class<?> valueClass) {
        return CodeBlock.builder()
            .addStatement(
                "$T tags = new $T<>()",
                createNamespacedSetMap(ClassName.get(valueClass)),
                HashMap.class
            );
    }

    private final CodeBlock.Builder blockTagCodeBlocks = createTagCodeBlock(Material.class);
    private final CodeBlock.Builder itemTagCodeBlocks = createTagCodeBlock(Material.class);
    private final CodeBlock.Builder entityTagCodeBlocks = createTagCodeBlock(EntityType.class);

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
        MethodSpec defaultBlockTags = createDefaultTagMethod("defaultBlockTags", Material.class, blockTagCodeBlocks);
        MethodSpec defaultItemTags = createDefaultTagMethod("defaultItemTags", Material.class, itemTagCodeBlocks);
        MethodSpec defaultEntityTags = createDefaultTagMethod("defaultEntityTags", EntityType.class, entityTagCodeBlocks);

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
                "tags.put($S, new $T<>()).putAll($N())",
                org.bukkit.Tag.REGISTRY_BLOCKS,
                HashMap.class,
                defaultBlockTags
            )
            .addStatement(
                "tags.put($S, new $T<>()).putAll($N())",
                org.bukkit.Tag.REGISTRY_ITEMS,
                HashMap.class,
                defaultItemTags
            )
            .addStatement(
                "tags.put($S, new $T<>()).putAll($N())",
                "entityTypes",
                HashMap.class,
                defaultEntityTags
            )
            .addStatement("return tags")
            .build();

        TypeSpec tagManagerTypeSpec = TypeSpec.classBuilder("TagManager")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(AbstractTagManager.class)
            .addMethod(defaultBlockTags)
            .addMethod(defaultItemTags)
            .addMethod(defaultEntityTags)
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
    
    private MethodSpec createDefaultTagMethod(String methodName, Class<?> valueType, CodeBlock.Builder tagCodeBlock) {
        tagCodeBlock.addStatement("return tags");
        return MethodSpec
            .methodBuilder(methodName)
            .addModifiers(Modifier.PRIVATE)
            .returns(createNamespacedSetMap(ClassName.get(valueType)))
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
            Optional<CodeBlock.Builder> directValueBlock = Optional.empty();
            Optional<CodeBlock.Builder> subTagValueBlock = Optional.empty();
            for (String tagValue : tag.getValues()) {
                if (tagValue.startsWith("#")) {
                    CodeBlock.Builder block;
                    if (subTagValueBlock.isPresent()) {
                        block = subTagValueBlock.get().add(", ");
                    } else {
                        block = CodeBlock.builder().add(
                            "$T.newHashSet(",
                            Sets.class
                        );
                        subTagValueBlock = Optional.of(block);
                    }
                    NamespacedKey namespacedKey = NamespaceUtils.parseNamespace(tagValue.substring(1), namespaceName);
                    block.add(
                        "getTagFromMap(tags, $S, $S)",
                        namespacedKey.getNamespace(),
                        namespacedKey.getKey()
                    );
                } else {
                    CodeBlock.Builder block;
                    if (directValueBlock.isPresent()) {
                        block = directValueBlock.get().add(", ");
                    } else {
                        block = CodeBlock.builder().add(
                            "$T.newHashSet(",
                            Sets.class
                        );
                        directValueBlock = Optional.of(block);
                    }
                    block.add(
                        "$T.$L",
                        valueType,
                        enumConverter.apply(tagValue)
                    );
                }
            }

            CodeBlock directValues = directValueBlock
                .map(builder -> builder.add(")").build())
                .orElseGet(() -> CodeBlock.of(
                    "$T.emptySet()",
                    Collections.class
                ));

            CodeBlock subTagValues = subTagValueBlock
                .map(builder -> builder.add(")").build())
                .orElseGet(() -> CodeBlock.of(
                    "$T.emptySet()",
                    Collections.class
                ));

            tagBlock.addStatement(
                "this.<$T>addTagToMap(tags, $S, $S, $L, $L)",
                valueType,
                namespaceName,
                itemName,
                directValues,
                subTagValues
            );
        }
    }
}
