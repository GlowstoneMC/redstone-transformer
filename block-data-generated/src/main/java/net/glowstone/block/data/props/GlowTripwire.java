package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.TNT;
import org.bukkit.block.data.type.Tripwire;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowTripwire.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = Tripwire.class
)
public interface GlowTripwire extends StatefulBlockData, Tripwire {
    class Constants {
        public static final String PROP_NAME = "disarmed";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isDisarmed() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setDisarmed(boolean extended) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, extended);
    }
}
