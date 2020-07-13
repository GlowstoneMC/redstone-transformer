package net.glowstone.datapack.processor.generation.utils;

import net.glowstone.datapack.loader.model.external.recipe.Item;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class ItemUtils {
    public static Stream<String> untagItem(Map<String, Map<String, Set<String>>> namespacedTaggedItems,
                                                String namespaceName,
                                                Item item) {
        Optional<String> tag = item.getTag();

        if (tag.isPresent()) {
            String tagName = tag.get();

            String[] namespacedKey = tagName.split(":", 2);
            if (namespacedKey.length == 1) {
                namespacedKey = new String[] {namespaceName, namespacedKey[0]};
            }

            return namespacedTaggedItems.get(namespacedKey[0])
                .get(namespacedKey[1])
                .stream()
                .flatMap(
                    (tagValue) -> {
                        if (!tagValue.startsWith("#")) {
                            return Stream.of(tagValue);
                        }

                        return untagItem(namespacedTaggedItems, namespaceName, new Item(Optional.empty(), Optional.of(tagValue.substring(1))));
                    }
                );
        } else {
            return Stream.of(item.getItem().get());
        }
    }
}
