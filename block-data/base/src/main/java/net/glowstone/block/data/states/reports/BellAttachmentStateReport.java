package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.type.Bell;

public class BellAttachmentStateReport extends EnumStateReport<Bell.Attachment> {
    public BellAttachmentStateReport(String defaultValue, String... validValues) {
        super(Bell.Attachment.class, defaultValue, validValues);
    }
}
