package net.glowstone.datapack.tags.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.tag.Tag;
import net.glowstone.datapack.tags.SubTagTrackingTag;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import net.glowstone.datapack.utils.mapping.TagInstanceMapping;
import org.bukkit.Material;

import java.util.List;
import java.util.stream.Collectors;

public class ItemTagMapping extends AbstractTagMapping<Material> {
    @Override
    public List<MappingArgument> providerArguments(String registry, String namespace, String key, Tag tag) {
        return generateValuesAndTags(
            namespace,
            tag,
            Material::matchMaterial,
            (values, tags) -> ImmutableList.of(
                MappingArgument.forString(registry),
                MappingArgument.forString(namespace),
                MappingArgument.forString(key),
                MappingArgument.forClassReference(Material.class),
                MappingArgument.forSet(
                    values.stream().map(MappingArgument::forEnum).collect(Collectors.toSet())
                ),
                MappingArgument.forSet(
                    tags.stream().map((t) -> MappingArgument.forTag(org.bukkit.Tag.REGISTRY_ITEMS, t, Material.class)).collect(Collectors.toSet())
                )
            )
        );
    }

    @Override
    public org.bukkit.Tag<Material> provider(AbstractTagManager tagManager, String registry, String namespace, String key, Tag tag) {
        return generateValuesAndTags(
            namespace,
            tag,
            Material::matchMaterial,
            (values, tags) -> tagManager.addTag(
                registry,
                namespace,
                key,
                Material.class,
                values,
                tags.stream().map(tagManager::getItemTag).collect(Collectors.toSet())
            )
        );
    }
}
