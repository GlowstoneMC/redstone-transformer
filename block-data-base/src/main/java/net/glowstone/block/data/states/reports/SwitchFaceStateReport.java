package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Switch;

public class SwitchFaceStateReport extends EnumStateReport<Switch.Face> {
    public SwitchFaceStateReport(String defaultValue, String... validValues) {
        super(Switch.Face.class, defaultValue, validValues);
    }
}
