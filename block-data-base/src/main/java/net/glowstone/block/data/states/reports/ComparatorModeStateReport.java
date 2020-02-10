package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Comparator;

public class ComparatorModeStateReport extends EnumStateReport<Comparator.Mode> {
    public ComparatorModeStateReport(String defaultValue, String... validValues) {
        super(Comparator.Mode.class, defaultValue, validValues);
    }
}
