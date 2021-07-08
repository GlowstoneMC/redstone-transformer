package net.glowstone.block.data.states.reports;

import org.bukkit.block.BlockFace;

public class BlockFaceStateReport extends EnumStateReport<BlockFace> {
    public BlockFaceStateReport(String defaultValue, String... validValues) {
        super(BlockFace.class, defaultValue, validValues);
    }
}
