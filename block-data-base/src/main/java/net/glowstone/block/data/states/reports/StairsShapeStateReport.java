package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Stairs;

public class StairsShapeStateReport extends EnumStateReport<Stairs.Shape> {
    public StairsShapeStateReport(String defaultValue, String... validValues) {
        super(Stairs.Shape.class, defaultValue, validValues);
    }
}
