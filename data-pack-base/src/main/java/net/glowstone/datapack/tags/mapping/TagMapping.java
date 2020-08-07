package net.glowstone.datapack.tags.mapping;

import net.glowstone.datapack.AbstractTagManager;
import net.glowstone.datapack.loader.model.external.tag.Tag;
import net.glowstone.datapack.utils.mapping.MappingArgument;
import org.bukkit.Keyed;

import java.util.List;

public interface TagMapping<T extends Keyed> {
    BlockTagMapping BLOCK = new BlockTagMapping();
    EntityTagMapping ENTITY = new EntityTagMapping();
    ItemTagMapping ITEM = new ItemTagMapping();

    List<MappingArgument> providerArguments(String registry, String namespace, String key, Tag tag);
    org.bukkit.Tag<T> provider(AbstractTagManager tagManager, String registry, String namespace, String key, Tag tag);
}
