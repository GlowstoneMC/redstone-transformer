package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.RespawnAnchor;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowRespawnAnchor.Constants.PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceClass = RespawnAnchor.class
)
public interface GlowRespawnAnchor extends StatefulBlockData, RespawnAnchor {
    class Constants {
        public static final String PROP_NAME = "charges";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getCharges() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setCharges(int charges) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, charges);
    }

    @Override
    default int getMaximumCharges() {
        return getMaxValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
