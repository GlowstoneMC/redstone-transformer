package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.DaylightDetector;
import org.bukkit.block.data.type.Dispenser;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowDispenser.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = Dispenser.class
)
public interface GlowDispenser extends StatefulBlockData, Dispenser {
    class Constants {
        public static final String PROP_NAME = "triggered";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isTriggered() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setTriggered(boolean triggered) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, triggered);
    }
}
