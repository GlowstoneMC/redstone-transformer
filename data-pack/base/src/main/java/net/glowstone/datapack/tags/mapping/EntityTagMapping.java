package net.glowstone.datapack.tags.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.tag.Tag;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import net.glowstone.datapack.utils.NamespaceUtils;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.stream.Collectors;

public class EntityTagMapping extends AbstractTagMapping<EntityType> {
    @Override
    public List<MappingArgument> providerArguments(String registry, String namespace, String key, Tag tag) {
        return generateValuesAndTags(
            namespace,
            tag,
            (name) -> EntityType.fromName(NamespaceUtils.parseNamespace(name, namespace).getKey()),
            (values, tags) -> ImmutableList.of(
                MappingArgument.forString(registry),
                MappingArgument.forString(namespace),
                MappingArgument.forString(key),
                MappingArgument.forClassReference(EntityType.class),
                MappingArgument.forSet(
                    values.stream().map(MappingArgument::forEnum).collect(Collectors.toSet())
                ),
                MappingArgument.forSet(
                    tags.stream().map((t) -> MappingArgument.forTag("entityTypes", t, EntityType.class)).collect(Collectors.toSet())
                )
            )
        );
    }

    @Override
    public org.bukkit.Tag<EntityType> provider(AbstractTagManager tagManager, String registry, String namespace, String key, Tag tag) {
        return generateValuesAndTags(
            namespace,
            tag,
            (name) -> EntityType.fromName(NamespaceUtils.parseNamespace(name, namespace).getKey()),
            (values, tags) -> tagManager.addTag(
                registry,
                namespace,
                key,
                EntityType.class,
                values,
                tags.stream().map(tagManager::getEntityTag).collect(Collectors.toSet())
            )
        );
    }
}
