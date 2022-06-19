package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.PointedDripstone;

public class ThicknessStateReport extends EnumStateReport<PointedDripstone.Thickness> {
    public ThicknessStateReport(String defaultValue, String... validValues) {
        super(PointedDripstone.Thickness.class, defaultValue, validValues);
    }
}
