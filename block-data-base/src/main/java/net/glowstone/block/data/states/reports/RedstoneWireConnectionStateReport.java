package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.RedstoneWire;

public class RedstoneWireConnectionStateReport extends EnumStateReport<RedstoneWire.Connection> {
    public RedstoneWireConnectionStateReport(String defaultValue, String... validValues) {
        super(RedstoneWire.Connection.class, defaultValue, validValues);
    }
}
