package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Slab;

public class SlabTypeStateReport extends EnumStateReport<Slab.Type> {
    public SlabTypeStateReport(String defaultValue, String... validValues) {
        super(Slab.Type.class, defaultValue, validValues);
    }
}
