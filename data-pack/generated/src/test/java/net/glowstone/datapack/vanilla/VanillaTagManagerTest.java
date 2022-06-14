package net.glowstone.datapack.vanilla;

import net.glowstone.datapack.EmptyTagManager;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.DataPackLoader;
import net.glowstone.datapack.loader.model.external.DataPack;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.google.common.truth.OptionalSubject.optionals;

public class VanillaTagManagerTest {
    @Test
    void testConstructingTagManager() {
        new VanillaTagManager();
    }

    @Test
    void testLoadingDataPack() {
        Path dataPackPath = Paths.get(System.getProperty("vanilla.generated.directory"));

        DataPackLoader dataPackLoader = new DataPackLoader();
        Optional<DataPack> dataPack = dataPackLoader.loadPack(dataPackPath);

        assertThat(dataPack.isPresent()).isTrue();

        TagManager loadingTagManager = new EmptyTagManager();
        loadingTagManager.loadFromDataPack(dataPack.get());

        TagManager vanillaTagManager = new VanillaTagManager();

        assertThat(vanillaTagManager.getAllTagRegistries())
            .isEqualTo(loadingTagManager.getAllTagRegistries());

        for (String registry : vanillaTagManager.getAllTagRegistries()) {
            assertWithMessage("Registry '%s'", registry)
                .that(vanillaTagManager.getAllKeysInRegistry(registry))
                .isEqualTo(loadingTagManager.getAllKeysInRegistry(registry));

            for (NamespacedKey key : vanillaTagManager.getAllKeysInRegistry(registry)) {
                assertWithMessage("Registry '%s', Key '%s'", registry, key.toString())
                    .about(optionals())
                    .that(vanillaTagManager.getTag(registry, key))
                    .isEqualTo(loadingTagManager.getTag(registry, key));
            }
        }
    }
}
