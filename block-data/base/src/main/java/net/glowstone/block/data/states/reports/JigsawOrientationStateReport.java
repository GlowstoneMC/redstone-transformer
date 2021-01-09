package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Jigsaw;

public class JigsawOrientationStateReport extends EnumStateReport<Jigsaw.Orientation> {
    public JigsawOrientationStateReport(String defaultValue, String... validValues) {
        super(Jigsaw.Orientation.class, defaultValue, validValues);
    }
}
