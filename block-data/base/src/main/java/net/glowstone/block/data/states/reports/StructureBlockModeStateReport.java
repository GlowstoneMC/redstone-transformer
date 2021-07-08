package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.StructureBlock;

public class StructureBlockModeStateReport extends EnumStateReport<StructureBlock.Mode> {
    public StructureBlockModeStateReport(String defaultValue, String... validValues) {
        super(StructureBlock.Mode.class, defaultValue, validValues);
    }
}
