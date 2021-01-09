package net.glowstone.datapack.tags.mapping;

import net.glowstone.datapack.loader.model.external.tag.Tag;
import net.glowstone.datapack.utils.NamespaceUtils;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractTagMapping<T extends Keyed> implements TagMapping<T> {
    protected <T extends Keyed, R> R generateValuesAndTags(String namespace, Tag tag, Function<String, T> valueParser, BiFunction<Set<T>, List<NamespacedKey>, R> generator) {
        Set<T> materials = new HashSet<>();
        List<NamespacedKey> tags = new ArrayList<>();

        tag.getValues().forEach((value) -> {
            if (value.startsWith("#")) {
                tags.add(NamespaceUtils.parseNamespace(value.substring(1), namespace));
            } else {
                materials.add(valueParser.apply(value));
            }
        });

        return generator.apply(materials, tags);
    }
}
