package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.SculkSensor;

public class SculkSensorPhaseStateReport extends EnumStateReport<SculkSensor.Phase> {
    public SculkSensorPhaseStateReport(String defaultValue, String... validValues) {
        super(SculkSensor.Phase.class, defaultValue, validValues);
    }
}
