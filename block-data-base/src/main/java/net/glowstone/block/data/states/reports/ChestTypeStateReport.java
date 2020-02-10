package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Chest;

public class ChestTypeStateReport extends EnumStateReport<Chest.Type> {
    public ChestTypeStateReport(String defaultValue, String... validValues) {
        super(Chest.Type.class, defaultValue, validValues);
    }
}
