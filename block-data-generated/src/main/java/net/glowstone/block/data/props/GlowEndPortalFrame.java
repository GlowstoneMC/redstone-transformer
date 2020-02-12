package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.block.data.type.EndPortalFrame;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowEndPortalFrame.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "EndPortalFrame"
)
public interface GlowEndPortalFrame extends StatefulBlockData, EndPortalFrame {
    class Constants {
        public static final String PROP_NAME = "eye";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean hasEye() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setEye(boolean eye) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, eye);
    }
}
