package net.glowstone.block.data.states.reports;

import org.bukkit.Axis;

public class AxisStateReport extends EnumStateReport<Axis> {
    public AxisStateReport(String defaultValue, String... validValues) {
        super(Axis.class, defaultValue, validValues);
    }
}
