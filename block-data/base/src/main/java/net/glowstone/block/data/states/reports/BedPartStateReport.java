package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Bed;

public class BedPartStateReport extends EnumStateReport<Bed.Part> {
    public BedPartStateReport(String defaultValue, String... validValues) {
        super(Bed.Part.class, defaultValue, validValues);
    }
}
