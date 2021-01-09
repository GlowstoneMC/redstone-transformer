package net.glowstone.block.data.states.reports;

import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.type.Switch;

public class AttachedFaceStateReport extends EnumStateReport<FaceAttachable.AttachedFace> {
    public AttachedFaceStateReport(String defaultValue, String... validValues) {
        super(FaceAttachable.AttachedFace.class, defaultValue, validValues);
    }
}
