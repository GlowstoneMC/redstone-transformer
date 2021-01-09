package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Wall;

public class WallHeightStateReport extends EnumStateReport<Wall.Height> {
    public WallHeightStateReport(String defaultValue, String... validValues) {
        super(Wall.Height.class, defaultValue, validValues);
    }
}
