package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Bamboo;

public class BambooLeavesStateReport extends EnumStateReport<Bamboo.Leaves> {
    public BambooLeavesStateReport(String defaultValue, String... validValues) {
        super(Bamboo.Leaves.class, defaultValue, validValues);
    }
}
