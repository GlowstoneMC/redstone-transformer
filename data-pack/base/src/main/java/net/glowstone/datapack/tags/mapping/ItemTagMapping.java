package net.glowstone.datapack.tags.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.tag.Tag;
import net.glowstone.datapack.utils.mapping.AbstractMappingArgument;
import net.glowstone.datapack.utils.mapping.ClassReferenceMappingArgument;
import net.glowstone.datapack.utils.mapping.EnumMappingArgument;
import net.glowstone.datapack.utils.mapping.SetMappingArgument;
import net.glowstone.datapack.utils.mapping.StringMappingArgument;
import net.glowstone.datapack.utils.mapping.TagMappingArgument;
import org.bukkit.Material;

import java.util.List;
import java.util.stream.Collectors;

public class ItemTagMapping extends AbstractTagMapping<Material> {
    @Override
    public List<AbstractMappingArgument> providerArguments(String registry, String namespace, String key, Tag tag) {
        return generateValuesAndTags(
            namespace,
            tag,
            Material::matchMaterial,
            (values, tags) -> ImmutableList.of(
                new StringMappingArgument(registry),
                new StringMappingArgument(namespace),
                new StringMappingArgument(key),
                new ClassReferenceMappingArgument(Material.class),
                new SetMappingArgument(
                    values.stream().map(EnumMappingArgument::new).collect(Collectors.toSet())
                ),
                new SetMappingArgument(
                    tags.stream().map((t) -> new TagMappingArgument(org.bukkit.Tag.REGISTRY_ITEMS, t, Material.class)).collect(Collectors.toSet())
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
