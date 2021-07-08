package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Door;

public class HingeStateReport extends EnumStateReport<Door.Hinge> {
    public HingeStateReport(String defaultValue, String... validValues) {
        super(Door.Hinge.class, defaultValue, validValues);
    }
}
