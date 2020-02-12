package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import org.bukkit.block.data.Attachable;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowAttachable.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "Attachable"
)
public interface GlowAttachable extends StatefulBlockData, Attachable {
    class Constants {
        public static final String PROP_NAME = "attached";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isAttached() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setAttached(boolean attached) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, attached);
    }
}
