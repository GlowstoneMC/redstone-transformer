package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Beehive;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowBeehive.Constants.PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceName = "Beehive"
)
public interface GlowBeehive extends StatefulBlockData, Beehive {
    class Constants {
        public static final String PROP_NAME = "honey_level";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getHoneyLevel() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setHoneyLevel(int honeyLevel) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, honeyLevel);
    }

    @Override
    default int getMaximumHoneyLevel() {
        return getMaxValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
