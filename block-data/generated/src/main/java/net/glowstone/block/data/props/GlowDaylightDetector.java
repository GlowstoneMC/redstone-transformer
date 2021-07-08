package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.block.data.type.DaylightDetector;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowDaylightDetector.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = DaylightDetector.class
)
public interface GlowDaylightDetector extends StatefulBlockData, DaylightDetector {
    class Constants {
        public static final String PROP_NAME = "inverted";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isInverted() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setInverted(boolean inverted) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, inverted);
    }
}
