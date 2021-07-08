package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.Rail;

public class RailShapeStateReport extends EnumStateReport<Rail.Shape> {
    public RailShapeStateReport(String defaultValue, String... validValues) {
        super(Rail.Shape.class, defaultValue, validValues);
    }
}
