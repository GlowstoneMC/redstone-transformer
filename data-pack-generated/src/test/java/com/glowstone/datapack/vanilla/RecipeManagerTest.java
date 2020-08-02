package com.glowstone.datapack.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Server;
import org.bukkit.inventory.ItemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecipeManagerTest {
    @BeforeEach
    void setUp() {
        Server server = mock(Server.class);
        ItemFactory itemFactory = mock(ItemFactory.class);
        Logger logger = mock(Logger.class);

        when(server.getLogger()).thenReturn(logger);
        when(server.getItemFactory()).thenReturn(itemFactory);
        when(itemFactory.getDefaultLeatherColor()).thenReturn(Color.fromRGB(0xA06540));

        Bukkit.setServer(server);
    }

    @Test
    void testConstructingBlockDataManager() {
        new RecipeManager(new TagManager());
    }
}
