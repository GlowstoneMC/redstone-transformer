package net.glowstone.datapack.processor.generation.utils;

import org.bukkit.NamespacedKey;

public class NamespaceUtils {
    public static NamespacedKey parseNamespace(String toParse, String currentNamespace) {
        String[] namespacedKey = toParse.split(":", 2);
        if (namespacedKey.length == 1) {
            namespacedKey = new String[] {currentNamespace, namespacedKey[0]};
        }
        return new NamespacedKey(namespacedKey[0], namespacedKey[1]);
    }
}
