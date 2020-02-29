package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.type.Campfire;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowCampfire.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "Campfire"
)
public interface GlowCampfire extends StatefulBlockData, Campfire {
    class Constants {
        public static final String PROP_NAME = "signal_fire";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isSignalFire() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setSignalFire(boolean signalFire) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, signalFire);
    }
}
