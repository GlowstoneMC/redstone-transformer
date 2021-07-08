package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.TechnicalPiston;

public class TechnicalPistonTypeStateReport extends EnumStateReport<TechnicalPiston.Type> {
    public TechnicalPistonTypeStateReport(String defaultValue, String... validValues) {
        super(TechnicalPiston.Type.class, defaultValue, validValues);
    }
}
