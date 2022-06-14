package net.glowstone.datapack.vanilla;

import net.glowstone.datapack.EmptyRecipeManager;
import net.glowstone.datapack.EmptyTagManager;
import net.glowstone.datapack.RecipeManager;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.DataPackLoader;
import net.glowstone.datapack.loader.model.external.DataPack;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.ItemFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import static com.google.common.truth.OptionalSubject.optionals;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.google.common.truth.Truth8.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class VanillaRecipeManagerTest {
    @BeforeAll
    static void beforeAll() {
        // This is to guard against pollution by other tests.
        try {
            Server server = mock(Server.class);
            Logger logger = mock(Logger.class);
            when(server.getLogger()).thenReturn(logger);
            when(server.getName()).thenReturn("mockServer");
            when(server.getVersion()).thenReturn("0.0.0");
            when(server.getBukkitVersion()).thenReturn("0");
            Bukkit.setServer(server);
        } catch (UnsupportedOperationException e) {
            // Ignored
        }
    }

    @BeforeEach
    void setUp() {
        reset(Bukkit.getServer());
        ItemFactory itemFactory = mock(ItemFactory.class);
        Logger logger = mock(Logger.class);

        when(itemFactory.equals(any(), isNull()))
            .thenAnswer((invocation) -> invocation.getArguments()[0] == null);

        when(Bukkit.getServer().getItemFactory()).thenReturn(itemFactory);
        when(Bukkit.getServer().getLogger()).thenReturn(logger);
        when(itemFactory.getDefaultLeatherColor()).thenReturn(Color.fromRGB(0xA06540));
    }

    @Test
    void testConstructingRecipeManager() {
        new VanillaRecipeManager(new VanillaTagManager());
    }

    @Test
    void testLoadingDataPack() {
        Path dataPackPath = Paths.get(System.getProperty("vanilla.generated.directory"));

        DataPackLoader dataPackLoader = new DataPackLoader();
        Optional<DataPack> dataPack = dataPackLoader.loadPack(dataPackPath);

        assertThat(dataPack).isPresent();

        TagManager loadingTagManager = new EmptyTagManager();
        RecipeManager loadingRecipeManager = new EmptyRecipeManager(loadingTagManager);
        loadingTagManager.loadFromDataPack(dataPack.get());
        loadingRecipeManager.loadFromDataPack(dataPack.get());

        TagManager vanillaTagManager = new VanillaTagManager();
        RecipeManager vanillaRecipeManager = new VanillaRecipeManager(vanillaTagManager);

        assertThat(vanillaRecipeManager.getAllRecipeKeys())
            .isEqualTo(loadingRecipeManager.getAllRecipeKeys());

        for (NamespacedKey key : vanillaRecipeManager.getAllRecipeKeys()) {
            assertWithMessage("Key '%s'", key.toString())
                .that(vanillaRecipeManager.getRecipeProvider(key))
                .isEqualTo(loadingRecipeManager.getRecipeProvider(key));
        }
    }
}
