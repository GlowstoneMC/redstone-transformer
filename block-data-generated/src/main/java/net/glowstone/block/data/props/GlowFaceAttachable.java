package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.AttachedFaceStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.FaceAttachable;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowFaceAttachable.Constants.PROP_NAME,
            reportType = AttachedFaceStateReport.class
        )
    },
    interfaceClass = FaceAttachable.class
)
public interface GlowFaceAttachable extends StatefulBlockData, FaceAttachable {
    class Constants {
        public static final String PROP_NAME = "face";
        public static final Class<AttachedFace> STATE_TYPE = AttachedFace.class;
    }

    @Override
    default AttachedFace getAttachedFace() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setAttachedFace(AttachedFace face) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, face);
    }
}
