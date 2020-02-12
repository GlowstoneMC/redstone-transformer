package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Hopper;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowHopper.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "Hopper"
)
public interface GlowHopper extends StatefulBlockData, Hopper {
    class Constants {
        public static final String PROP_NAME = "enabled";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isEnabled() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setEnabled(boolean enabled) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, enabled);
    }
}
